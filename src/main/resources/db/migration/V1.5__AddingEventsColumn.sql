create table public.event_participants (
  user_id bigint NOT NULL,
  event_id bigint NOT NULL,
  primary key(user_id, event_id));

create table public.events (
  id bigint NOT NULL,
  all_day boolean,
  start varchar(255),
  title varchar(255),
  primary key(id));

alter table news
  add column text varchar(255);

alter table user_wishes
  add column event_for_wish bigint;

alter table event_participants
  add constraint FK2x391urx4up03f4jp2y9mdt5x foreign key(event_id)references events;

alter table event_participants
  add constraint FKre6m0d4mgt4351tytlkac9jvf foreign key(user_id)references users;

alter table user_wishes
  add constraint FKq7bw0fhmlxvogmf9wocmuekek foreign key(event_for_wish)references events;