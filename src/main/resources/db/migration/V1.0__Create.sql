SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';


CREATE TABLE public.chat(
                            current_price double precision,
                            deadline timestamp without time zone,
                            description character varying(255),
                            present_price double precision,
                            wish_for_chat bigint NOT NULL,
                            news bigint,
                            user_id bigint
);


ALTER TABLE public.chat OWNER TO giftsweb_user;

CREATE TABLE public.chat_chat_for_participants(
                                                  chat_wish_for_chat    bigint NOT NULL,
                                                  chat_for_participants bigint NOT NULL
);


ALTER TABLE public.chat_chat_for_participants OWNER TO giftsweb_user;

CREATE TABLE public.chat_messages(
                                     chat_wish_for_chat bigint NOT NULL,
                                     messages           bigint NOT NULL
);


ALTER TABLE public.chat_messages OWNER TO giftsweb_user;

CREATE TABLE public.chat_participants(
                                         chat_id bigint NOT NULL,
                                         user_id bigint NOT NULL
);


ALTER TABLE public.chat_participants OWNER TO giftsweb_user;


CREATE SEQUENCE public.hibernate_sequence
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO giftsweb_user;

CREATE TABLE public.interests (
                                  id bigint NOT NULL,
                                  interest_name character varying(255)
);


ALTER TABLE public.interests OWNER TO giftsweb_user;


CREATE SEQUENCE public.interests_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.interests_id_seq OWNER TO giftsweb_user;


ALTER SEQUENCE public.interests_id_seq OWNED BY public.interests.id;


CREATE TABLE public.messages (
                                 id bigint NOT NULL,
                                 text character varying(255),
                                 user_id bigint,
                                 wish_for_chat bigint
);


ALTER TABLE public.messages OWNER TO giftsweb_user;

CREATE SEQUENCE public.messages_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.messages_id_seq OWNER TO giftsweb_user;

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;

CREATE TABLE public.news(
                            id      bigint NOT NULL,
                            chat_id bigint
);


ALTER TABLE public.news OWNER TO giftsweb_user;

CREATE SEQUENCE public.news_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.news_id_seq OWNER TO giftsweb_user;


ALTER SEQUENCE public.news_id_seq OWNED BY public.news.id;


CREATE TABLE public.news_users(
                                  saw   boolean,
                                  news  bigint NOT NULL,
                                  users bigint NOT NULL
);


ALTER TABLE public.news_users OWNER TO giftsweb_user;


CREATE TABLE public.participants(
                                    id                    bigint NOT NULL,
                                    sum_from_user         double precision NOT NULL,
                                    chat_for_participants bigint,
                                    participants_for_chat bigint
);


ALTER TABLE public.participants OWNER TO giftsweb_user;


CREATE TABLE public.tbl_friends(
                                   friend_id bigint NOT NULL,
                                   user_id   bigint NOT NULL
);


ALTER TABLE public.tbl_friends OWNER TO giftsweb_user;

CREATE TABLE public.user_has_interest(
                                         user_id     bigint NOT NULL,
                                         interest_id bigint NOT NULL
);


ALTER TABLE public.user_has_interest OWNER TO giftsweb_user;


CREATE TABLE public.user_info (
                                  id bigint NOT NULL,
                                  birthday date,
                                  first_name character varying(255),
                                  last_name character varying(255),
                                  user_id bigint
);


ALTER TABLE public.user_info OWNER TO giftsweb_user;

CREATE SEQUENCE public.user_info_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.user_info_id_seq OWNER TO giftsweb_user;


ALTER SEQUENCE public.user_info_id_seq OWNED BY public.user_info.id;

CREATE TABLE public.user_role (
                                  user_id bigint NOT NULL, roles character varying(255)
);


ALTER TABLE public.user_role OWNER TO giftsweb_user;


CREATE TABLE public.user_wishes (
                                    id bigint NOT NULL,
                                    wish_name character varying(255),
                                    user_id bigint
);


ALTER TABLE public.user_wishes OWNER TO giftsweb_user;


CREATE SEQUENCE public.user_wishes_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.user_wishes_id_seq OWNER TO giftsweb_user;


ALTER SEQUENCE public.user_wishes_id_seq OWNED BY public.user_wishes.id;


CREATE TABLE public.users (
                              id bigint NOT NULL,
                              activation_code character varying(255),
                              confirmed boolean NOT NULL,
                              email character varying(255),
                              password character varying(255),
                              user_calendar_id character varying(255),
                              username character varying(255),
                              vk_id bigint
);


ALTER TABLE public.users OWNER TO giftsweb_user;


CREATE TABLE public.users_chats_owner(
                                         user_id     bigint NOT NULL,
                                         chats_owner bigint NOT NULL
);


ALTER TABLE public.users_chats_owner OWNER TO giftsweb_user;


CREATE SEQUENCE public.users_id_seq
    START
        WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO giftsweb_user;


ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


CREATE TABLE public.users_messages(
                                      user_id  bigint NOT NULL,
                                      messages bigint NOT NULL
);


ALTER TABLE public.users_messages OWNER TO giftsweb_user;


CREATE TABLE public.users_participants_for_chat(
                                                   user_id               bigint NOT NULL,
                                                   participants_for_chat bigint NOT NULL
);


ALTER TABLE public.users_participants_for_chat OWNER TO giftsweb_user;


CREATE TABLE public.users_wishes(
                                    user_id bigint NOT NULL,
                                    wishes  bigint NOT NULL
);


ALTER TABLE public.users_wishes OWNER TO giftsweb_user;


ALTER TABLE ONLY public.interests
    ALTER COLUMN id
        SET DEFAULT nextval('public.interests_id_seq'::regclass);


ALTER TABLE ONLY public.messages
    ALTER COLUMN id
        SET DEFAULT nextval('public.messages_id_seq'::regclass);


ALTER TABLE ONLY public.news
    ALTER COLUMN id
        SET DEFAULT nextval('public.news_id_seq'::regclass);



ALTER TABLE ONLY public.user_info
    ALTER COLUMN id
        SET DEFAULT nextval('public.user_info_id_seq'::regclass);



ALTER TABLE ONLY public.user_wishes
    ALTER COLUMN id
        SET DEFAULT nextval('public.user_wishes_id_seq'::regclass);

ALTER TABLE ONLY public.users
    ALTER COLUMN id
        SET DEFAULT nextval('public.users_id_seq'::regclass);

ALTER TABLE ONLY public.chat_chat_for_participants
    ADD CONSTRAINT chat_chat_for_participants_pkey PRIMARY KEY(chat_wish_for_chat, chat_for_participants);



ALTER TABLE ONLY public.chat_messages
    ADD CONSTRAINT chat_messages_pkey PRIMARY KEY(chat_wish_for_chat, messages);


ALTER TABLE ONLY public.chat_participants
    ADD CONSTRAINT chat_participants_pkey PRIMARY KEY(chat_id, user_id);

ALTER TABLE ONLY public.chat
    ADD CONSTRAINT chat_pkey PRIMARY KEY(wish_for_chat);




ALTER TABLE ONLY public.interests
    ADD CONSTRAINT interests_pkey PRIMARY KEY(id);


ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY(id);



ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pkey PRIMARY KEY(id);

ALTER TABLE ONLY public.news_users
    ADD CONSTRAINT news_users_pkey PRIMARY KEY(news, users);

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_pkey PRIMARY KEY(id);


ALTER TABLE ONLY public.tbl_friends
    ADD CONSTRAINT tbl_friends_pkey PRIMARY KEY(user_id, friend_id);



ALTER TABLE ONLY public.users_wishes
    ADD CONSTRAINT uk_14xpf1qxgfufqwt88o7fhtm1s UNIQUE(wishes);


ALTER TABLE ONLY public.users_participants_for_chat
    ADD CONSTRAINT uk_b0gsxdajr9jcga5vpp6yb0tge UNIQUE(participants_for_chat);




ALTER TABLE ONLY public.chat_chat_for_participants
    ADD CONSTRAINT uk_cqiukrg88yjxug55mmm6o9x3j UNIQUE(chat_for_participants);


ALTER TABLE ONLY public.users_chats_owner
    ADD CONSTRAINT uk_gb6ju2dihry2bitoxlsvkujvc UNIQUE(chats_owner);


ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT uk_hitv0gjnvv936w7875nm2q5gd UNIQUE(messages);



ALTER TABLE ONLY public.chat_messages
    ADD CONSTRAINT uk_orbc2bnfukln3v136on2ajte2 UNIQUE(messages);


ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE(username);



ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_tg3mxfd6khpqshjtx1n7gqho5 UNIQUE(vk_id);



ALTER TABLE ONLY public.user_has_interest
    ADD CONSTRAINT user_has_interest_pkey PRIMARY KEY(user_id, interest_id);



ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY(id);



ALTER TABLE ONLY public.user_wishes
    ADD CONSTRAINT user_wishes_pkey PRIMARY KEY(id);



ALTER TABLE ONLY public.users_chats_owner
    ADD CONSTRAINT users_chats_owner_pkey PRIMARY KEY(user_id, chats_owner);



ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT users_messages_pkey PRIMARY KEY(user_id, messages);



ALTER TABLE ONLY public.users_participants_for_chat
    ADD CONSTRAINT users_participants_for_chat_pkey PRIMARY KEY(user_id, participants_for_chat);



ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY(id);



ALTER TABLE ONLY public.users_wishes
    ADD CONSTRAINT users_wishes_pkey PRIMARY KEY(user_id, wishes);


ALTER TABLE ONLY public.tbl_friends
    ADD CONSTRAINT fk19oduj4ki70keb3droooa5nuk FOREIGN KEY(user_id)REFERENCES public.users(id);


ALTER TABLE ONLY public.users_chats_owner
    ADD CONSTRAINT fk1qlyt55qepuaua3k8ieilfmtn FOREIGN KEY(chats_owner)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.chat
    ADD CONSTRAINT fk1x766u663l7m0mxuj0o72muu FOREIGN KEY(user_id)REFERENCES public.users(id)
        ON DELETE CASCADE;

ALTER TABLE ONLY public.users_chats_owner
    ADD CONSTRAINT fk34gs2gh81axhp5da1yqwf21o5 FOREIGN KEY(user_id)REFERENCES public.users(id)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.tbl_friends
    ADD CONSTRAINT fk37u6tdourritrsbwlq88ie1xt FOREIGN KEY(friend_id)REFERENCES public.users(id);


ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT fk3tpfu7mudlhpcrh6xufdkexha FOREIGN KEY(user_id)REFERENCES public.users(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fk46k1s8smmrjai295eulgyngww FOREIGN KEY(wish_for_chat)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.chat_messages
    ADD CONSTRAINT fk48nsh4tnm8p03jkeqk5ajb7os FOREIGN KEY(messages)REFERENCES public.messages(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.users_participants_for_chat
    ADD CONSTRAINT fk7nfci8jocasdhxhdek08j74ec FOREIGN KEY(participants_for_chat)REFERENCES public.participants(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.users_wishes
    ADD CONSTRAINT fk9ptkhy9pgw7hf281b9qel5031 FOREIGN KEY(wishes)REFERENCES public.user_wishes(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.users_messages
    ADD CONSTRAINT fka77hy4o72nxq6sito5i3q6h3c FOREIGN KEY(messages)REFERENCES public.messages(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.chat_messages
    ADD CONSTRAINT fkavdv6pl2k9mt62ffc3gl5x3s1 FOREIGN KEY(chat_wish_for_chat)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.user_wishes
    ADD CONSTRAINT fkc60q3rn80amypa1my0vlbueli FOREIGN KEY(user_id)REFERENCES public.users(id);



ALTER TABLE ONLY public.users_wishes
    ADD CONSTRAINT fke4vhf0awlw9tj80yfp1jmjuqa FOREIGN KEY(user_id)REFERENCES public.users(id);



ALTER TABLE ONLY public.user_has_interest
    ADD CONSTRAINT fkf13ijb29cl9mc6uwxj0vkbgr4 FOREIGN KEY(user_id)REFERENCES public.users(id);


ALTER TABLE ONLY public.user_has_interest
    ADD CONSTRAINT fkfj2ypnxdvevea2aq9nbubr73k FOREIGN KEY(interest_id)REFERENCES public.interests(id);


ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY(user_id)REFERENCES public.users(id);



ALTER TABLE ONLY public.chat
    ADD CONSTRAINT fklhc9k72sa9cj17tku51tvf7ip FOREIGN KEY(news)REFERENCES public.news(id)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.chat_chat_for_participants
    ADD CONSTRAINT fklyl2iyuodsfo7mqayjt6ljowb FOREIGN KEY(chat_wish_for_chat)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.news_users
    ADD CONSTRAINT fknlfpsdxum9qen414nvhy4bt0s FOREIGN KEY(users)REFERENCES public.users(id);



ALTER TABLE ONLY public.users_participants_for_chat
    ADD CONSTRAINT fko1bshcmddk10o7af5h836gyxk FOREIGN KEY(user_id)REFERENCES public.users(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.chat_chat_for_participants
    ADD CONSTRAINT fko85x99m9cjbolcvj342mv0y57 FOREIGN KEY(chat_for_participants)REFERENCES public.participants(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.participants
    ADD CONSTRAINT fkokn2ve28y3y7xnvom50gw3g3f FOREIGN KEY(participants_for_chat)REFERENCES public.users(id)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.participants
    ADD CONSTRAINT fkoshlyw4r4nq6dmy1jlma87lhe FOREIGN KEY(chat_for_participants)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.chat
    ADD CONSTRAINT fkpdnae06hij47whyxg8agv3lxx FOREIGN KEY(wish_for_chat)REFERENCES public.user_wishes(id)
        ON DELETE CASCADE;


ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fkpsmh6clh3csorw43eaodlqvkn FOREIGN KEY(user_id)REFERENCES public.users(id)
        ON DELETE CASCADE;



ALTER TABLE ONLY public.news_users
    ADD CONSTRAINT fkr0hurjw20kst75jh4t0r8nuck FOREIGN KEY(news)REFERENCES public.news(id);


ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT fkr1b96ca4asuvrhwoqkdmbo7nj FOREIGN KEY(user_id)REFERENCES public.users(id);


ALTER TABLE ONLY public.news
    ADD CONSTRAINT fksgtlgjybdf5nsrxsuestllmo2 FOREIGN KEY(chat_id)REFERENCES public.chat(wish_for_chat)
        ON DELETE CASCADE;
