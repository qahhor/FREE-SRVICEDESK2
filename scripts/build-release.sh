#!/bin/bash
# Build and release script for Service Desk Platform
# Usage: ./scripts/build-release.sh [--docker] [--skip-tests]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default options
BUILD_DOCKER=false
SKIP_TESTS=false

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --docker)
            BUILD_DOCKER=true
            shift
            ;;
        --skip-tests)
            SKIP_TESTS=true
            shift
            ;;
        --help)
            echo "Usage: $0 [OPTIONS]"
            echo ""
            echo "Options:"
            echo "  --docker      Build Docker images after Maven build"
            echo "  --skip-tests  Skip running tests"
            echo "  --help        Show this help message"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            exit 1
            ;;
    esac
done

echo -e "${GREEN}=== Service Desk Platform Build ===${NC}"
echo ""

# Check Java version
echo -e "${YELLOW}Checking Java version...${NC}"
java -version 2>&1 | head -1
JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}Error: Java 17 or higher is required${NC}"
    exit 1
fi
echo ""

# Check Maven
echo -e "${YELLOW}Checking Maven version...${NC}"
mvn -version | head -1
echo ""

cd "$PROJECT_ROOT/backend"

# Build with Maven
if [ "$SKIP_TESTS" = true ]; then
    echo -e "${YELLOW}Building project (skipping tests)...${NC}"
    mvn -T 1C clean package -DskipTests -B
else
    echo -e "${YELLOW}Building project with tests...${NC}"
    mvn -T 1C clean verify -B
fi
echo ""

echo -e "${GREEN}Build artifacts created:${NC}"
find . -name "*.jar" -path "*/target/*" -type f | grep -v original | while read jar; do
    echo "  - $jar"
done
echo ""

# Build Docker images if requested
if [ "$BUILD_DOCKER" = true ]; then
    echo -e "${YELLOW}Building Docker images...${NC}"
    
    # Check if Docker is available
    if ! command -v docker &> /dev/null; then
        echo -e "${RED}Error: Docker is not installed or not in PATH${NC}"
        exit 1
    fi
    
    # Build ticket-service
    echo -e "${YELLOW}Building ticket-service Docker image...${NC}"
    docker build -t servicedesk/ticket-service:latest ticket-service/
    
    # Build ai-service
    echo -e "${YELLOW}Building ai-service Docker image...${NC}"
    docker build -t servicedesk/ai-service:latest ai-service/
    
    echo ""
    echo -e "${GREEN}Docker images created:${NC}"
    docker images | grep servicedesk
fi

echo ""
echo -e "${GREEN}=== Build Complete ===${NC}"
