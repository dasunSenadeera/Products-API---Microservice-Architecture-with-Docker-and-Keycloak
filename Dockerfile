# Use the official Maven image to build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Run Maven to build the application
RUN mvn clean install -DskipTests

# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files of each module from the build stage
COPY --from=build /app/discovery-server/target/discovery-server-*.jar discovery-server.jar
COPY --from=build /app/api-gateway/target/api-gateway-*.jar api-gateway.jar
COPY --from=build /app/product-search-service/target/product-search-service-*.jar product-search-service.jar
COPY --from=build /app/product-setup-service/target/product-setup-service-*.jar product-setup-service.jar

# Copy wait-for-it.sh
COPY scripts/wait-for-it.sh /app/
RUN chmod +x /app/wait-for-it.sh

# Copy and set permissions for entrypoint script
COPY scripts/entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh

# Use JSON CMD to avoid shell issues
CMD ["/app/entrypoint.sh"]