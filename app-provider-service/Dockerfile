FROM openjdk:11.0.2
EXPOSE 8045
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app-provider-service.jar
ENTRYPOINT ["java","-jar","/app-provider-service.jar"]