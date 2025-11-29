#!/bin/bash
# End-to-End Test Script for Service Desk Platform
# Usage: ./scripts/e2e-test.sh [--base-url URL]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default settings
AI_SERVICE_URL="${AI_SERVICE_URL:-http://localhost:8084}"
TICKET_SERVICE_URL="${TICKET_SERVICE_URL:-http://localhost:8081}"
TIMEOUT=30
TESTS_PASSED=0
TESTS_FAILED=0

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --ai-url)
            AI_SERVICE_URL="$2"
            shift 2
            ;;
        --ticket-url)
            TICKET_SERVICE_URL="$2"
            shift 2
            ;;
        --timeout)
            TIMEOUT="$2"
            shift 2
            ;;
        --help)
            echo "Usage: $0 [OPTIONS]"
            echo ""
            echo "Options:"
            echo "  --ai-url URL       AI service base URL (default: http://localhost:8084)"
            echo "  --ticket-url URL   Ticket service base URL (default: http://localhost:8081)"
            echo "  --timeout SECONDS  Timeout for health checks (default: 30)"
            echo "  --help             Show this help message"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            exit 1
            ;;
    esac
done

# Check required tools
check_tool() {
    if ! command -v "$1" &> /dev/null; then
        echo -e "${RED}Error: $1 is required but not installed${NC}"
        exit 1
    fi
}

check_tool curl
check_tool jq

echo -e "${GREEN}=== Service Desk E2E Tests ===${NC}"
echo ""
echo "AI Service URL:     $AI_SERVICE_URL"
echo "Ticket Service URL: $TICKET_SERVICE_URL"
echo ""

# Helper function to run a test
run_test() {
    local name="$1"
    local result="$2"
    
    if [ "$result" = "0" ]; then
        echo -e "${GREEN}✓${NC} $name"
        ((TESTS_PASSED++))
    else
        echo -e "${RED}✗${NC} $name"
        ((TESTS_FAILED++))
    fi
}

# Helper function to check if service is healthy
wait_for_service() {
    local url="$1"
    local name="$2"
    local attempt=0
    local max_attempts=$((TIMEOUT / 2))
    
    echo -e "${YELLOW}Waiting for $name to be ready...${NC}"
    
    while [ $attempt -lt $max_attempts ]; do
        if curl -sf "$url" > /dev/null 2>&1; then
            echo -e "${GREEN}$name is ready${NC}"
            return 0
        fi
        attempt=$((attempt + 1))
        sleep 2
    done
    
    echo -e "${RED}$name did not become ready within ${TIMEOUT}s${NC}"
    return 1
}

echo -e "${YELLOW}=== Health Checks ===${NC}"
echo ""

# Check AI Service health
if curl -sf "${AI_SERVICE_URL}/actuator/health" > /dev/null 2>&1; then
    run_test "AI Service health check" 0
else
    # Try without actuator
    if curl -sf "${AI_SERVICE_URL}/v3/api-docs" > /dev/null 2>&1; then
        run_test "AI Service health check (via OpenAPI)" 0
    else
        run_test "AI Service health check" 1
    fi
fi

# Check Ticket Service health
if curl -sf "${TICKET_SERVICE_URL}/actuator/health" > /dev/null 2>&1; then
    run_test "Ticket Service health check" 0
else
    # Try basic endpoint
    if curl -sf "${TICKET_SERVICE_URL}/api/v1/tickets" > /dev/null 2>&1; then
        run_test "Ticket Service health check (via API)" 0
    else
        run_test "Ticket Service health check" 1
    fi
fi

echo ""
echo -e "${YELLOW}=== AI Service Tests ===${NC}"
echo ""

# Test AI classification endpoint
CLASSIFY_RESPONSE=$(curl -sf -X POST "${AI_SERVICE_URL}/api/v1/ai/classify" \
    -H "Content-Type: application/json" \
    -d '{"text": "I have a billing issue with my invoice"}' 2>/dev/null || echo "FAILED")

if [ "$CLASSIFY_RESPONSE" != "FAILED" ] && echo "$CLASSIFY_RESPONSE" | jq -e '.labels' > /dev/null 2>&1; then
    run_test "AI classification endpoint" 0
    echo "  Response: $CLASSIFY_RESPONSE"
else
    run_test "AI classification endpoint" 1
fi

# Test AI summarization endpoint
SUMMARIZE_RESPONSE=$(curl -sf -X POST "${AI_SERVICE_URL}/api/v1/ai/summarize" \
    -H "Content-Type: application/json" \
    -d '{"text": "This is a long text that needs to be summarized for testing purposes."}' 2>/dev/null || echo "FAILED")

if [ "$SUMMARIZE_RESPONSE" != "FAILED" ] && echo "$SUMMARIZE_RESPONSE" | jq -e '.summary' > /dev/null 2>&1; then
    run_test "AI summarization endpoint" 0
    echo "  Response: $SUMMARIZE_RESPONSE"
else
    run_test "AI summarization endpoint" 1
fi

echo ""
echo -e "${YELLOW}=== Ticket Service Tests ===${NC}"
echo ""

# Test ticket creation
TICKET_RESPONSE=$(curl -sf -X POST "${TICKET_SERVICE_URL}/api/v1/tickets" \
    -H "Content-Type: application/json" \
    -d '{"title": "E2E Test Ticket", "description": "This is a test ticket for E2E testing with hardware issue"}' 2>/dev/null || echo "FAILED")

if [ "$TICKET_RESPONSE" != "FAILED" ]; then
    # Check if ticket has id and labels
    if echo "$TICKET_RESPONSE" | jq -e '.id' > /dev/null 2>&1; then
        run_test "Ticket creation endpoint" 0
        echo "  Response: $TICKET_RESPONSE"
        
        # Check if labels were applied via AI integration
        LABELS=$(echo "$TICKET_RESPONSE" | jq -r '.labels // []')
        if [ "$LABELS" != "[]" ] && [ "$LABELS" != "null" ]; then
            run_test "AI integration (labels applied)" 0
            echo "  Labels: $LABELS"
        else
            run_test "AI integration (labels applied)" 1
            echo "  Note: Labels may not be applied if AI service is not running"
        fi
    else
        run_test "Ticket creation endpoint" 1
    fi
else
    run_test "Ticket creation endpoint" 1
fi

# Test ticket listing
LIST_RESPONSE=$(curl -sf "${TICKET_SERVICE_URL}/api/v1/tickets" 2>/dev/null || echo "FAILED")

if [ "$LIST_RESPONSE" != "FAILED" ]; then
    run_test "Ticket listing endpoint" 0
else
    run_test "Ticket listing endpoint" 1
fi

echo ""
echo -e "${YELLOW}=== Results ===${NC}"
echo ""
echo -e "Tests passed: ${GREEN}$TESTS_PASSED${NC}"
echo -e "Tests failed: ${RED}$TESTS_FAILED${NC}"
echo ""

if [ $TESTS_FAILED -gt 0 ]; then
    echo -e "${RED}Some tests failed${NC}"
    exit 1
else
    echo -e "${GREEN}All tests passed${NC}"
    exit 0
fi
