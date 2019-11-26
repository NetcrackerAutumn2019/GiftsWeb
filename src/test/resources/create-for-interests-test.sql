delete from user_has_interest;
delete from user_info;
delete from users;

insert into users (id, confirmed, email, password, username) values
(1, true, 'a', 'a', 'a'),
(2, true, 'b', 'b', 'b');

insert into user_info (id, first_name, last_name, user_id) values
(1, 'A', 'A', 1);

insert into user_has_interest (user_id, interest_id) values
(2, 11);
