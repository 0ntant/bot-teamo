FROM postgres:14.13

EXPOSE 5432

ENV POSTGRES_PASSWORD admin
ENV POSTGRES_USER admin
ENV POSTGRES_DB teamo_db

COPY initRedQueenDatabase.sql /docker-entrypoint-initdb.d/
COPY initContentStorageServiceDatabase.sql /docker-entrypoint-initdb.d/
COPY initKeycloakDatabase.sql /docker-entrypoint-initdb.d/
