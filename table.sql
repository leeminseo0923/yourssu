use yourssu;

create table user
(
    user_id    bigint auto_increment not null,
    created_at datetime              not null,
    updated_at datetime              not null,
    email      varchar(255)          not null,
    password   varchar(255)          not null,
    username   varchar(255)          not null,
    primary key (user_id)
);


create table article
(
    article_id bigint auto_increment not null,
    created_at datetime              not null,
    updated_at datetime              not null,
    content    varchar(255)          not null,
    title      varchar(255)          not null,
    user_id    bigint                not null,
    primary key (article_id),
    foreign key (user_id) references user (user_id)
);


create table comment
(
    comment_id bigint auto_increment not null,
    created_at datetime              not null,
    updated_at datetime              not null,
    content    varchar(255)          not null,
    article_id bigint                not null,
    user_id    bigint                not null,
    primary key (comment_id),
    foreign key (article_id) references article (article_id),
    foreign key (user_id) references user (user_id)
);

