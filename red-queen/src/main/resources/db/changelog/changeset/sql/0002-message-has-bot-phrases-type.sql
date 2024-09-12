--liquibase formatted sql

--changeset 0ntant:2
ALTER TABLE messages
    ADD COLUMN bot_phrases_type_id INTEGER,
    ADD FOREIGN KEY (bot_phrases_type_id) REFERENCES bot_phrases_type(id);

--rollback ALTER TABLE messages DROP COLUMN bot_phrases_type_id;
