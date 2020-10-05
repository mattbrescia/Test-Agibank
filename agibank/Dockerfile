FROM openjdk:11-jdk-slim
ADD /src/target/agibank.jar agibank.jar
EXPOSE 3333
ENTRYPOINT ["java", "-jar", "/agibank.jar"]