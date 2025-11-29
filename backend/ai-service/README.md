# AI Service

AI and LLM integration service for the Service Desk Platform. Provides text classification and summarization capabilities.

## Features

- **Text Classification**: Analyze text and return relevant labels for categorization
- **Text Summarization**: Generate summaries of longer text content
- **Provider Switching**: Support for mock provider (development) and OpenAI provider (production)

## Running the Service

### Prerequisites

- Java 17 or higher
- Maven 3.8+

### Start the Service

```bash
cd backend/ai-service
mvn spring-boot:run
```

The service will start on **http://localhost:8084**.

## API Documentation (Swagger UI)

Once the service is running, access the API documentation at:

- **Swagger UI**: http://localhost:8084/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8084/v3/api-docs
- **OpenAPI YAML**: http://localhost:8084/v3/api-docs.yaml

## API Endpoints

### POST /api/v1/ai/classify

Classify text and return relevant labels.

**Request:**
```json
{
  "text": "My computer won't start and the screen is black"
}
```

**Response:**
```json
{
  "labels": ["hardware", "critical"]
}
```

### POST /api/v1/ai/summarize

Summarize text content.

**Request:**
```json
{
  "text": "This is a long description of a technical issue..."
}
```

**Response:**
```json
{
  "summary": "Technical issue summary..."
}
```

## Provider Configuration

The service supports two providers:

### Mock Provider (Default)

Used when `OPENAI_API_KEY` is not set or empty. Provides keyword-based classification for development and testing.

### OpenAI Provider

To enable OpenAI provider, set the `OPENAI_API_KEY` environment variable:

```bash
export OPENAI_API_KEY=your-api-key-here
mvn spring-boot:run
```

Or configure in `application.yml`:

```yaml
app:
  ai:
    provider: openai
    openai:
      api-key: ${OPENAI_API_KEY}
      model: gpt-4o
```

## Configuration

Default configuration in `application.yml`:

```yaml
server:
  port: 8084

app:
  ai:
    provider: openai  # or anthropic
    openai:
      api-key: ${OPENAI_API_KEY:}
      model: gpt-4o
    anthropic:
      api-key: ${ANTHROPIC_API_KEY:}
      model: claude-3-opus-20240229
```

## Testing

Run tests with:

```bash
mvn test
```
