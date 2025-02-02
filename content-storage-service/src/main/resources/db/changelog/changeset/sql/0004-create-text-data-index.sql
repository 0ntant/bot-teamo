--liquibase formatted sql

--changeset 0ntant:4


CREATE INDEX t_text_data_text_content_idx ON t_text_data USING GIN (text_content gin_trgm_ops);

