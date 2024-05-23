truncate table bot_phrases_type cascade;
truncate table user_teamo cascade;

insert into user_teamo (id, name, sys_create_date)
values
(1, 'Аня', 		now()),
(2, 'Антон', 	now()),
(3, 'Виктор', 	now());

INSERT INTO bot_phrases_type 
values 
	(1,'Title_type_1'),
	(2,'Title_type_2'),
	(3,'Title_type_3'),
	(4,'Title_type_4');
	

INSERT INTO bot_phrases
values 
	(1, 'body_1', 1 , 'female'),
	(2, 'body_2', 2 , 'female'),
	(3, 'body_3', 3 , 'female'),
	(4, 'body_4', 3 , 'female'),
	(5, 'body_5', 3 , 'female'),
	(6, 'body_6', 1 , 'female'),
	(7, 'body_7', 4 , 'female');
