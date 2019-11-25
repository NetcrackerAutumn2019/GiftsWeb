delete from participants;
delete from chat;
delete from user_wishes;
delete from user_info;
delete from users;

insert into users (id, confirmed, email, password, username) values
(1, true, 'a', 'a', 'a'),
(2, true, 'b', 'b', 'b');

insert into user_info (id, first_name, last_name, user_id) values
(1, 'A', 'A', 1),
(2, 'B', 'B', 2);

insert into user_wishes(id, wish_name, user_id, friend_create_wish) values
(1, 'chocolate', 1, false),
(2, 'book', 2, false);

insert into chat (current_price, description, present_price, wish_for_chat, user_id) values
(0.0, 'AAA', 150, 1, 2);

insert into participants (id, chat_for_participants, participants_for_chat, sum_from_user) values
(1, 1, 2, 0.0);