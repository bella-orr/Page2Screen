# syntax=docker/dockerfile:1.7-labs

ARG GRADLE_IMAGE=gradle:9.1.0-jdk25
ARG RUNTIME_IMAGE=eclipse-temurin:25-jre

FROM ${GRADLE_IMAGE} AS build
WORKDIR /workspace/app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle bootJar --no-daemon

FROM ${RUNTIME_IMAGE} AS runtime
WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=default
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
