DROP DATABASE IF EXISTS content_storage_service_db;
CREATE DATABASE content_storage_service_db;

\c content_storage_service_db
CREATE USER content_storage_service_app  WITH PASSWORD '7(<6@~*!:N;35XTS<gk$hUIW-%%d9t,SRtHH';

create table object_sum
(
	id SERIAL PRIMARY KEY,
	object_hash varchar(255) NOT NULL UNIQUE
);

create table t_img_avatar
(
     object_sum_id SERIAL PRIMARY KEY REFERENCES object_sum(id),
     path varchar(255) NOT NULL UNIQUE,
     gender varchar(63) NOT NULL,
     sys_create_date TIMESTAMP NOT NULL
);

GRANT SELECT, UPDATE, DELETE, insert, references, truncate
ON
    object_sum,
	t_img_avatar
TO content_storage_service_app;

ALTER TABLE object_sum OWNER TO content_storage_service_app;
ALTER TABLE t_img_avatar OWNER TO content_storage_service_app;

GRANT ALL ON ALL SEQUENCES
IN SCHEMA public
TO content_storage_service_app;

GRANT USAGE, CREATE
ON schema  public
to content_storage_service_app;
