FROM gradle:8.11-jdk21 AS build
WORKDIR /app

COPY ../../build.gradle.kts ../../settings.gradle.kts ./
COPY ../../src/main ./src/main

RUN gradle build --parallel --no-daemon -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT java -jar /app/app.jar
