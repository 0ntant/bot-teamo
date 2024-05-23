truncate table messages cascade;
truncate table user_teamo cascade;

insert into user_teamo (id, name, sys_create_date)
values 
(1, 'Аня', now()),
(2, 'Антон', now()),
(3, 'Виктор', now());

insert into messages
values
(1,  'body_1', '2023-01-01', 1, 2),
(2,  'body_2', '2023-01-02', 2, 1),
(3,  'body_3', '2023-01-03', 1, 2),

(4,  'body_4', '2023-01-05', 2, 3),
(5,  'body_4', '2023-01-06', 3, 2),
(6,  'body_5', '2023-01-07', 3, 2),
(7,  'body_6', '2023-01-08', 2, 3);
