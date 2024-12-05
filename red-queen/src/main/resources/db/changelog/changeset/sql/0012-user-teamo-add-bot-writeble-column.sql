--liquibase formatted sql

--changeset 0ntant:12
BEGIN;
ALTER TABLE user_teamo
ADD COLUMN bot_writable boolean;

UPDATE user_teamo
SET bot_writable = true;

ALTER TABLE user_teamo
ALTER COLUMN bot_writable SET NOT NULL;
COMMIT;
