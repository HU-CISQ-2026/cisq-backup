FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/uno-api-0.0.1.jar"]
