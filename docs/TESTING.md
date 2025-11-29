# Testing Guide

This guide covers how to run and write tests for the Service Desk Platform.

## Test Types

The platform includes:

1. **Unit Tests** - Fast, isolated tests for individual components
2. **Integration Tests** - Tests that verify component interactions
3. **End-to-End Tests** - Full flow tests (planned)

## Running Tests

### All Tests

Run all tests across all modules:

```bash
cd backend
mvn test
```

### Specific Module Tests

Run tests for a specific module:

```bash
# AI Service tests
mvn test -pl ai-service -am

# Ticket Service tests
mvn test -pl ticket-service -am
```

### Single Test Class

Run a specific test class:

```bash
mvn test -pl ai-service -Dtest=MockAiProviderTest
```

### With Coverage

Run tests with code coverage report:

```bash
mvn test jacoco:report
```

Coverage reports are generated in `target/site/jacoco/index.html`.

## Test Structure

### Unit Tests

#### AI Service Tests

| Test Class | Description |
|------------|-------------|
| `MockAiProviderTest` | Tests deterministic mock responses |
| `AiServiceTest` | Tests service delegation to provider |
| `AiControllerTest` | Tests REST endpoints with MockMvc |

Example test:

```java
@Test
@DisplayName("classify returns billing labels for billing-related text")
void classify_returnsBillingLabels() {
    List<String> labels = provider.classify("I have a question about my billing");
    assertThat(labels).containsExactly("billing", "finance");
}
```

#### Ticket Service Tests

| Test Class | Description |
|------------|-------------|
| `AiClientTest` | Tests AI service communication with MockWebServer |
| `TicketControllerTest` | Tests ticket operations with mocked AI client |
| `TicketTest` | Tests ticket model |
| `CreateTicketRequestTest` | Tests request DTO |

Example test:

```java
@Test
void createTicket_shouldReturnTicketWithLabels() {
    List<String> mockLabels = Arrays.asList("hardware", "urgent");
    when(aiClient.classify(anyString())).thenReturn(mockLabels);

    CreateTicketRequest request = new CreateTicketRequest(
            "Computer not starting",
            "My computer won't start and the screen is black"
    );

    ResponseEntity<Ticket> response = ticketController.createTicket(request);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(mockLabels, response.getBody().getLabels());
}
```

## Test Configuration

### AI Service Test Configuration

Tests use MockBean for service layer:

```java
@WebMvcTest(AiController.class)
class AiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AiService aiService;
}
```

### Ticket Service Test Configuration

Tests use H2 in-memory database and exclude Redis:

```yaml
# src/test/resources/application.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
```

## Writing Tests

### Best Practices

1. **Descriptive Names** - Use `@DisplayName` for readable test names
2. **Arrange-Act-Assert** - Structure tests clearly
3. **One Assertion Focus** - Each test should verify one behavior
4. **Mock External Dependencies** - Use Mockito for external services
5. **Use AssertJ** - For fluent assertions

### Example: Testing a New Feature

1. Create test class in appropriate package:

```java
package com.greenwhitesolutions.servicedesk.ai.provider;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NewProviderTest {
    @Test
    @DisplayName("should return expected classification")
    void testClassification() {
        // Arrange
        NewProvider provider = new NewProvider();
        
        // Act
        List<String> labels = provider.classify("test input");
        
        // Assert
        assertThat(labels).isNotEmpty();
    }
}
```

2. Run the test:

```bash
mvn test -Dtest=NewProviderTest
```

## Mocking

### MockWebServer

Used for testing HTTP clients:

```java
@BeforeAll
static void startServer() throws Exception {
    mockServer = new MockWebServer();
    mockServer.start();
}

@Test
void testHttpCall() {
    mockServer.enqueue(new MockResponse()
            .setBody("{\"labels\": [\"test\"]}")
            .addHeader("Content-Type", "application/json"));

    // Execute HTTP call and verify
}
```

### Mockito

Used for mocking services:

```java
@Mock
private AiProvider aiProvider;

@Test
void testWithMock() {
    when(aiProvider.classify(anyString())).thenReturn(List.of("label"));
    // Execute and verify
}
```

## CI Integration

Tests are automatically run on:

- Push to `main`, `feature/**`, `copilot/**` branches
- Pull requests to `main`

See `.github/workflows/ci.yml` for configuration.

## Troubleshooting

### Tests Not Running

1. Verify test class naming follows convention: `*Test.java`
2. Ensure JUnit 5 dependencies are included
3. Check for compilation errors

### Mock Issues

1. Ensure `@ExtendWith(MockitoExtension.class)` is present
2. Verify mock setup happens before test execution
3. Check mock return values match expected types

### Database Tests Failing

1. Verify H2 dependency is in test scope
2. Check `application.yml` in test resources
3. Ensure tables are created with `ddl-auto: create-drop`

## Future Improvements

- [ ] Add integration tests with Testcontainers
- [ ] Add E2E tests for complete workflows
- [ ] Increase code coverage to >80%
- [ ] Add WireMock for external API testing
- [ ] Add performance tests
