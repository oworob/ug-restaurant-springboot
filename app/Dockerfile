FROM maven:3.8.4 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

ENTRYPOINT ["java", "-jar", "target/app-1.jar"]
