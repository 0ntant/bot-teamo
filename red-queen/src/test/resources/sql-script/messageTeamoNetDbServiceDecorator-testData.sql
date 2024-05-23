
truncate table user_teamo cascade;
truncate table general_attribute cascade;
truncate table bot_phrases_type cascade;
truncate table bot_phrases cascade;

insert into user_teamo (id, name, sys_create_date)
values 
(21596288, 'Аня', now()),
(21593632, 'Антон', now());


INSERT INTO bot_phrases_type(id,title) VALUES
(1,'joke'),
(2,'get_contact'),
(3,'apologize');


INSERT INTO  bot_phrases(id, body, f_type_id, gender) VALUES
(1, 'MessageToJoke',1 ,'female');