truncate table messages cascade;
truncate table user_teamo cascade;
truncate table user_teamo_block cascade;

insert into user_teamo (id, name, sys_create_date)
values 
(1, 'Аня', 		now()),
(2, 'Антон', 	now()),
(3, 'Виктор', 	now());

insert into user_teamo (id, name, sys_create_date, "token")
values 
(4, 'Виктор', 	now(), 	'token_4'),
(5, 'Оля', 		now(),  'token_5'),
(6, 'Юля', 		now(),  'token_6');

insert into messages
values
(1,  'body_1', '2023-01-01', 1, 2),
(2,  'body_2', '2023-01-02', 2, 1),
(3,  'body_3', '2023-01-03', 1, 2),

(4,  'body_4', '2023-01-05', 2, 3),
(5,  'body_4', '2023-01-06', 3, 2),
(6,  'body_5', '2023-01-07', 3, 2),
(7,  'body_6', '2023-01-08', 2, 3);


insert into user_has_user_blacklist
values
(1 , 2),
(1 , 3);

insert into user_teamo_block (user_id, is_blocking,reason,teamo_code, block_check_date)
values
(1, false,'test reason', 1, now()),
(2, false,'test reason', 1, now()),
(3, false,'test reason', 1, now()),
(4, false,'test reason', 1, now()),
(5, false,'test reason', 1, now()),
(6, false,'test reason', 1, now());