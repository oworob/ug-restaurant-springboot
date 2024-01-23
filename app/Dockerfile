FROM openjdk:17 AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean install

ENTRYPOINT ["java", "-jar", "target/app-1.jar"]
