FROM maven:3.9.7-eclipse-temurin-17 as build

WORKDIR /build

COPY src/main/java src/main/java
COPY pom.xml pom.xml
COPY src/main/resources/application-test.yml ./application-test.yaml
COPY src/main/resources/logback-spring.xml ./resources/logback-spring.xml
COPY src/main/resources/templates ./resources/templates
COPY src/main/resources/static ./resources/static

RUN --mount=type=cache,target=/root/.m2  mvn clean package  \
    -DskipTests



FROM bellsoft/liberica-openjdk-debian:17.0.9-11

RUN addgroup java-group && adduser --ingroup java-group java-user

USER java-user
WORKDIR /app

RUN mkdir "images"

COPY --from=build /build/resources ./resources
COPY --from=build /build/application-test.yaml ./application-test.yaml
COPY --from=build /build/target/img-generator-1.0-SNAPSHOT.jar ./index.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "index.jar" , "--spring.config.location=./application-test.yaml"]