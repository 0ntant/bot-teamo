--liquibase formatted sql

--changeset 0ntant:5
alter table user_has_user_blacklist add constraint unique_user_id_user_in_blacklist_id unique ("user_id" , "user_in_blacklist_id");

--rollback alter table user_has_user_blacklist drop constraint unique_user_id_user_in_blacklist_id;