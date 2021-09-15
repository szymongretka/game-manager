FROM openjdk:11.0.2
EXPOSE 9000
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]