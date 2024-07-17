FROM maven:3.9.7-eclipse-temurin-17 as build

WORKDIR /build

COPY src/main/java src/main/java
COPY pom.xml pom.xml
COPY src/main/resources/application-test.yml ./application-test.yaml

RUN --mount=type=cache,target=/root/.m2  mvn clean package  \
    -DskipTests


FROM bellsoft/liberica-openjdk-debian:17.0.9-11

RUN addgroup java-group && adduser --ingroup java-group java-user

USER java-user
WORKDIR /app
RUN mkdir "resources"

COPY --from=build /build/application-test.yaml ./application-test.yaml
COPY --from=build /build/target/proxy-provider-1.0-SNAPSHOT.jar ./index.jar

ENTRYPOINT ["java", "-jar", "index.jar", "--spring.config.location=./application-test.yaml"]