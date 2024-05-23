insert into user_teamo
values 
(1, 'username1', 11, now(), now(), 'city1', 'zodiac1', 121, 'gender1', 'password1', 'token1', 'email1'),
(2, 'username1', 12, now(), now(), 'city2', 'zodiac2', 122, 'gender2', 'password2', 'token12', 'email2'),
(3, 'username1', 13, now(), now(), 'city3', 'zodiac3', 123, 'gender3', 'password3', 'token3', 'email3');


INSERT INTO user_teamo_block(user_id, is_blocking, reason, teamo_code, block_check_date)
VALUES
(1, false,'test reason', 1, now()),
(2,true, 'test reason', 1, now()),
(3,false, 'test reason', 1, now());


INSERT INTO messages(body, create_date, sender_id, receiver_id) 
values
('four',  '2023-04-03 09:03:01',  2 , 1 ),
('three', '2023-04-03 09:02:01',  1 , 2 ),
('two',   '2023-04-03 09:01:01',  2 , 1 ),
('one',   '2023-04-03 09:00:01',  1 , 2 );
