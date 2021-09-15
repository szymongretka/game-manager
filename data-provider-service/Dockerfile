FROM openjdk:11.0.2
EXPOSE 8050
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} data-provider-service.jar
ENTRYPOINT ["java","-jar","/data-provider-service.jar"]