create table label(
    id bigint auto_increment primary key,

    name varchar(128) not null,

    code varchar(128) not null,

    parent_code varchar(128),

    status tinyint not null default 0,

    auth tinyint not null default 0,

    create_time timestamp not null default current_timestamp,

    update_time timestamp not null default current_timestamp,

    unique (parent_code, name),

    unique (code)
);