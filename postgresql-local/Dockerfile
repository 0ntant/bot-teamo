FROM postgres:14.12

EXPOSE 5432

ENV POSTGRES_PASSWORD admin
ENV POSTGRES_USER admin
ENV POSTGRES_DB teamo_db

COPY ./initRedQueenDataBase.sql /docker-entrypoint-initdb.d/
COPY ./initContentStorageServiceDataBase.sql /docker-entrypoint-initdb.d/