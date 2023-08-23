drop database if exists tcs_library;
create database tcs_library;
use tcs_library;

drop table if exists app_user_role;
drop table if exists app_role;
drop table if exists app_user;
drop table if exists comments;
drop table if exists review;
drop table if exists item_shelf;
drop table if exists item;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table item (
	item_id int primary key auto_increment,
    identifier text,
    filename text
);

create table item_shelf (
	item_shelf_id int primary key auto_increment,
    page_number int default 1,
    item_id int,
    app_user_id int,
    constraint fk_item_shelf_item_id
		foreign key (item_id)
		references item(item_id),
	constraint fk_item_shelf_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id)
);

create table review (
	review_id int primary key auto_increment,
    review text,
    app_user_id int,
    item_id int,
    constraint fk_review_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_review_item_id
		foreign key (item_id)
        references item(item_id)
);

create table comments (
	comment_id int primary key auto_increment,
    comment_text text,
    app_user_id int, 
    review_id int,
    constraint fk_comment_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_comment_review_id
		foreign key (review_id)
        references review(review_id)
);  

insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 2),
    (2, 1);    

    
insert into item (identifier, filename) 
	values
    ("pride-and-prejudice_201907","pride-and-prejudice.pdf");
    
insert into item_shelf (page_number, item_id, app_user_id)
	values
    (303, 1, 2),
    (200, 1, 1);

insert into review(review, item_id, app_user_id)
	values
    ("Book was pretty cool ngl", 1, 1),
    ("Test review", 1, 1);

insert into comments(comment_text, app_user_id, review_id)
	values
    ("Yeah I thought so too", 1, 1);

select * from item_shelf;
SELECT * FROM item;
SELECT * FROM review;
