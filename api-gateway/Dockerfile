FROM openjdk:16-jdk-slim
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /gateway/apigateway.jar
CMD ["java", "-jar", "/gateway/apigateway.jar"]
EXPOSE 8082