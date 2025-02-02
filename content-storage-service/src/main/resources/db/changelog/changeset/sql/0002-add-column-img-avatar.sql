--liquibase formatted sql

--changeset 0ntant:2

BEGIN;
ALTER TABLE t_img_avatar
    ADD COLUMN create_source VARCHAR(125);

UPDATE t_img_avatar
    SET create_source = '1.11 update';

ALTER TABLE t_img_avatar
    ALTER COLUMN create_source SET NOT NULL;
COMMIT;

