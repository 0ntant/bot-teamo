--liquibase formatted sql

--changeset 0ntant:3

BEGIN;
    create table t_text_data
    (
         id SERIAL PRIMARY KEY,
         text_content TEXT NOT NULL,
         create_source varchar(255) NOT NULL,
         sys_create_date TIMESTAMP NOT NULL
    );

    create table t_data_tag
    (
         id SERIAL PRIMARY KEY ,
         title varchar(63) NOT NULL UNIQUE
    );

    CREATE TABLE text_data_has_tag
    (
        tag_id integer NOT NULL,
        text_data_id integer NOT NULL,

        FOREIGN KEY(tag_id) REFERENCES t_data_tag(id),
        FOREIGN KEY(text_data_id) REFERENCES t_text_data(id)
    );
COMMIT;