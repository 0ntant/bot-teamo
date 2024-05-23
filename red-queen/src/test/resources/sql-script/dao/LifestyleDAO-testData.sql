truncate table lifestyle  cascade;
truncate table lifestyle_type  cascade;
truncate table user_teamo  cascade;

insert into lifestyle_type 
values 
(1,'lable_1', 'title_1'),
(2, 'lable_2', 'title_2');


insert into user_teamo (id, name, sys_create_date)
values 
(1, 'Аня', now()),
(2, 'Антон', now());

insert into lifestyle  
values 
(1,'body_1',  1  ,1),
(2, 'body_2', 2, 2 );
