FROM openjdk:11.0.2
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]