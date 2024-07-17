--liquibase formatted sql

--changeset 0ntant:7
create table residence_place
(
	id SERIAL primary key,
	title varchar(255) not null unique,
	timezone varchar(255) not null,
	active boolean not null
);

--rollback  drop table residence_place ;
