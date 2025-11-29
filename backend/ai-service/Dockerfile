FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="Service Desk Platform"
LABEL description="AI Service - AI and LLM integration service"

# Create app directory
WORKDIR /app

# Create non-root user for security
RUN addgroup -S servicedesk && adduser -S servicedesk -G servicedesk

# Copy the JAR file
COPY target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R servicedesk:servicedesk /app

# Switch to non-root user
USER servicedesk

# Expose the service port
EXPOSE 8084

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8084/actuator/health || exit 1

# JVM settings for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
