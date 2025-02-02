FROM maven:3.9.9-eclipse-temurin-17 as build

WORKDIR /build

COPY src/main/java src/main/java
COPY pom.xml pom.xml
COPY src/main/resources/templates ./resources/templates
COPY src/main/resources/static ./resources/static

RUN --mount=type=cache,target=/root/.m2  mvn clean package  \
    -DskipTests


FROM bellsoft/liberica-openjdk-debian:17.0.13-12

WORKDIR /app

COPY --from=build /build/resources ./resources
COPY --from=build /build/target/img-generator-1.0-SNAPSHOT.jar ./index.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "index.jar"]
