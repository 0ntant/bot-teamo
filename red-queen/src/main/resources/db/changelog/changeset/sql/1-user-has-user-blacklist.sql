--liquibase formatted sql

--changeset 0ntant:1
create table user_has_user_blacklist
(
	user_id integer NOT NULL,
	user_in_blacklist_id integer NOT NULL,

	FOREIGN KEY(user_id) REFERENCES user_teamo(id),
	FOREIGN KEY(user_in_blacklist_id) REFERENCES user_teamo(id)
);

--rollback drop table user_has_user_blacklist;