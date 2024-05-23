--liquibase formatted sql

--changeset 0ntant:3
alter table t_like add constraint unique_like_name_text unique ("name", "text");
alter table t_dislikes add constraint unique_dislike_name_text unique ("name", "text");

--rollback alter table t_like drop constraint unique_like_name_text;
--rollback alter table t_dislikes drop constraint unique_dislike_name_text;