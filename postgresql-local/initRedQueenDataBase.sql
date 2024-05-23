--CREATE DATABASE IF NOT EXISTS teamo_db;

\c teamo_db

CREATE USER teamo_app  WITH PASSWORD 'MWQ*sdg#MNGW(Isd_gdH(#N!NG@($#sd(!?';

CREATE TABLE user_teamo
(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255),
	age integer , 
	last_visit TIMESTAMP, 
	sys_create_date TIMESTAMP NOT NULL,
	city varchar(255) ,
	zodiac varchar(255),
	height integer,
	gender varchar(255),
	password varchar(255),
	token varchar(255),
	email varchar(255) UNIQUE 
	
);


CREATE TABLE user_teamo_block
(
	 user_id SERIAL PRIMARY KEY REFERENCES user_teamo(id),
   	 is_blocking boolean,	
   	 reason varchar(255),
   	 teamo_code integer,
	 block_check_date TIMESTAMP
);

CREATE TABLE t_like
(
	id SERIAL PRIMARY KEY,
	f_type varchar (255) NOT NULL,
	name varchar(255) NOT NULL,
	text varchar(255) NOT NULL	 
);


CREATE TABLE t_dislikes
(
	id SERIAL PRIMARY KEY,
	f_type varchar (255) NOT NULL,
	name varchar(255) NOT NULL,
	text varchar(254) NOT NULL	 
);


CREATE TABLE photo
(
	id SERIAL PRIMARY KEY,
	url varchar(255) UNIQUE NOT NULL,
	f_path varchar(255) UNIQUE,
	user_id integer NOT NULL,
	
	FOREIGN KEY(user_id) REFERENCES user_teamo(id)
);

CREATE TABLE general_attribute
(
	value SERIAL PRIMARY KEY,
	name varchar(255) NOT NULL,
	value_text varchar(255) NOT NULL
);

--CREATE TABLE user_bot 
--(
--	id SERIAL PRIMARY KEY,
--	email varchar(255) UNIQUE NOT NULL,
--	username varchar(255) NOT NULL,
--	password varchar(255) NOT NULL,
--	age integer,
--	token varchar(255)	
--);


CREATE TABLE user_teamo_has_t_like
(
	user_id integer NOT NULL,
    t_like_id integer NOT NULL,

    FOREIGN KEY(user_id) REFERENCES user_teamo(id),
    FOREIGN KEY(t_like_id) REFERENCES t_like(id)
);


CREATE TABLE user_teamo_has_t_dislikes
(
	user_id integer NOT NULL,
    t_dislikes_id integer NOT NULL,

    FOREIGN KEY(user_id) REFERENCES user_teamo(id),
    FOREIGN KEY(t_dislikes_id) REFERENCES t_dislikes(id)
);


CREATE TABLE user_teamo_has_general_attribute
(
	user_id integer NOT NULL,
    general_attribute_id integer NOT NULL,

    FOREIGN KEY(user_id) REFERENCES user_teamo(id),
    FOREIGN KEY(general_attribute_id) REFERENCES general_attribute(value)
);


CREATE TABLE messages
(
	id SERIAL PRIMARY KEY,
	body text NOT NULL,
	create_date TIMESTAMP,
	sender_id integer NOT NULL,
	receiver_id integer NOT NULL,
	
	FOREIGN KEY(sender_id) REFERENCES user_teamo(id),
	FOREIGN KEY(receiver_id) REFERENCES user_teamo(id)
);


CREATE TABLE bot_phrases_type
(
	id SERIAL PRIMARY KEY,
	title varchar(255) NOT NULL
);


CREATE TABLE bot_phrases
(
	id SERIAL PRIMARY KEY,
	body varchar(511) NOT null unique,
	f_type_id integer NOT NULL,
	gender varchar(255),
	
	FOREIGN KEY (f_type_id) REFERENCES bot_phrases_type(id)
);


CREATE TABLE lifestyle_type
(
	id SERIAL PRIMARY KEY,	
	label varchar(255),
	title varchar(255) NOT NULL
);

CREATE TABLE lifestyle
(
	id SERIAL PRIMARY KEY, 
	body text NOT NULL,
	lifestyle_type_id integer NOT NULL,
	user_id integer NOT NULL,
	
	FOREIGN KEY(user_id) REFERENCES user_teamo(id),
	FOREIGN KEY (lifestyle_type_id) REFERENCES lifestyle_type(id)
);


CREATE TABLE looking_type
(
	id SERIAL PRIMARY KEY,	
	label varchar(255),
	title varchar(255) NOT NULL
);

CREATE TABLE user_looking
(
	id SERIAL PRIMARY KEY, 
	body varchar(255) NOT NULL,
	looking_type_id integer NOT NULL,
	user_id integer NOT NULL,
	
	FOREIGN KEY(user_id) REFERENCES user_teamo(id),
	FOREIGN KEY (looking_type_id) REFERENCES looking_type(id)
);

GRANT SELECT, UPDATE, DELETE, insert, references , truncate
ON 
	user_teamo, 
	t_like,
	t_dislikes,
	photo,
	general_attribute,
	user_teamo_has_t_like,
	user_teamo_has_t_dislikes,
	user_teamo_has_general_attribute,
	messages,
	user_teamo_block,
	bot_phrases_type,
	bot_phrases,
	lifestyle_type,
	lifestyle,
	looking_type,
	user_looking
TO teamo_app;

ALTER TABLE user_teamo OWNER TO teamo_app;
ALTER TABLE t_like OWNER TO teamo_app;
ALTER TABLE t_dislikes OWNER TO teamo_app;
ALTER TABLE photo OWNER TO teamo_app;
ALTER TABLE general_attribute  OWNER TO teamo_app;
ALTER TABLE user_teamo_has_t_like OWNER TO teamo_app;
ALTER TABLE user_teamo_has_t_dislikes OWNER TO teamo_app;
ALTER TABLE user_teamo_has_general_attribute OWNER TO teamo_app;
ALTER TABLE messages OWNER TO teamo_app;
ALTER TABLE user_teamo_block OWNER TO teamo_app;
ALTER TABLE bot_phrases_type OWNER TO teamo_app;
ALTER TABLE bot_phrases OWNER TO teamo_app;
ALTER TABLE lifestyle_type OWNER TO teamo_app;
ALTER TABLE lifestyle OWNER TO teamo_app;
ALTER TABLE looking_type OWNER TO teamo_app;
ALTER TABLE user_looking OWNER TO teamo_app;

GRANT ALL ON ALL SEQUENCES 
IN SCHEMA public
TO teamo_app;

GRANT USAGE, CREATE
ON schema  public
to teamo_app;

