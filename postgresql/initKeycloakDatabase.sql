DROP DATABASE IF EXISTS keycloak_db;
CREATE DATABASE keycloak;

\c keycloak
CREATE USER keycloak_app  WITH PASSWORD 'o9oxAIHeXfT1McvyfdhH';

GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak_app;