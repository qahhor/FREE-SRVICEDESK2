# Contributing Guide

Thank you for your interest in contributing to the Service Desk Platform! This guide will help you get started.

## Getting Started

### Prerequisites

Before contributing, ensure you have:

- Java 17+
- Maven 3.8+
- Docker and Docker Compose
- Git

### Setup

1. Fork the repository on GitHub
2. Clone your fork:
   ```bash
   git clone https://github.com/YOUR-USERNAME/FREE-SRVICEDESK2.git
   cd FREE-SRVICEDESK2
   ```
3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/qahhor/FREE-SRVICEDESK2.git
   ```
4. Install dependencies and verify build:
   ```bash
   cd backend
   mvn clean install
   ```

## Development Workflow

### 1. Create a Branch

Always create a feature branch from `main`:

```bash
git checkout main
git pull upstream main
git checkout -b feature/your-feature-name
```

Branch naming conventions:
- `feature/` - New features
- `fix/` - Bug fixes
- `docs/` - Documentation changes
- `test/` - Test additions
- `refactor/` - Code refactoring

### 2. Make Changes

Follow these guidelines:

- **Keep changes focused** - One feature/fix per PR
- **Write tests** - Add tests for new functionality
- **Update documentation** - Keep docs current
- **Follow code style** - See style guide below

### 3. Test Your Changes

Before committing:

```bash
# Run all tests
cd backend
mvn test

# Run specific module tests
mvn test -pl ai-service -am

# Verify build
mvn package -DskipTests
```

### 4. Commit Your Changes

Write clear commit messages:

```bash
git add .
git commit -m "type(scope): description"
```

Commit types:
- `feat` - New feature
- `fix` - Bug fix
- `docs` - Documentation
- `test` - Adding tests
- `refactor` - Code refactoring
- `chore` - Maintenance

Examples:
- `feat(ai): add text summarization endpoint`
- `fix(ticket): handle empty labels array`
- `docs: update installation guide`
- `test(ai): add MockAiProvider tests`

### 5. Push and Create PR

```bash
git push origin feature/your-feature-name
```

Then create a Pull Request on GitHub with:
- Clear title following commit conventions
- Description of changes
- Link to related issues

## Code Style

### Java

- Use 4 spaces for indentation
- Follow standard Java naming conventions
- Add Javadoc for public methods
- Use meaningful variable names

Example:

```java
/**
 * Classifies the given text and returns a list of labels.
 *
 * @param text the text to classify
 * @return list of classification labels
 */
public List<String> classify(String text) {
    log.debug("Classifying text of length: {}", text.length());
    return aiProvider.classify(text);
}
```

### Tests

- Use descriptive test names with `@DisplayName`
- Follow Arrange-Act-Assert pattern
- One assertion focus per test
- Use AssertJ for assertions

Example:

```java
@Test
@DisplayName("classify returns billing labels for billing-related text")
void classify_returnsBillingLabels() {
    // Arrange
    String text = "I have a question about my billing";
    
    // Act
    List<String> labels = provider.classify(text);
    
    // Assert
    assertThat(labels).containsExactly("billing", "finance");
}
```

### Configuration

- Use YAML for configuration files
- Environment variables for sensitive data
- Never commit secrets

## Pull Request Guidelines

### Before Submitting

- [ ] Tests pass locally
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] Commit messages are clear
- [ ] No merge conflicts

### PR Description Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Refactoring

## Testing
How to test these changes

## Checklist
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] No breaking changes
```

### Review Process

1. Automated CI checks run on PR
2. Maintainer reviews code
3. Address feedback if needed
4. PR merged after approval

## Reporting Issues

### Bug Reports

Include:
- Clear description
- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version)

### Feature Requests

Include:
- Use case description
- Proposed solution
- Alternative solutions considered

## Community Guidelines

- Be respectful and inclusive
- Follow code of conduct
- Help others learn
- Give constructive feedback

## Getting Help

- Review existing documentation
- Check existing issues
- Ask in discussions
- Contact maintainers

## License

By contributing, you agree that your contributions will be licensed under the MIT License.
