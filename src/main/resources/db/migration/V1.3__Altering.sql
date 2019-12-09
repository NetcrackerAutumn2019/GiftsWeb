 alter table user_info
  add column photo50 varchar(255);

 alter table user_wishes
  add column friend_create_wish boolean not null;