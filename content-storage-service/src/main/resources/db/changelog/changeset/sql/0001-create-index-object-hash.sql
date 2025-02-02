--liquibase formatted sql

--changeset 0ntant:1
create index idx_sha256hash on object_sum(object_hash);