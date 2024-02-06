#base image to start with
FROM openjdk:17-jdk-slim
#copy jar file from target folder to container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts.jar
#execute jar file
ENTRYPOINT ["java","-jar","accounts.jar"]