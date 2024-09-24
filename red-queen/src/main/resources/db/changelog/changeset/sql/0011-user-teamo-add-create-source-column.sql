--liquibase formatted sql

--changeset 0ntant:11
BEGIN;
ALTER TABLE user_teamo
ADD COLUMN create_source  varchar(125) ;

UPDATE user_teamo
SET create_source = CASE
    WHEN token IS NOT NULL THEN 'bot 1.7 app version'
    ELSE 'human 1.7 app version'
END;

ALTER TABLE user_teamo
ALTER COLUMN create_source SET NOT NULL;
COMMIT;
