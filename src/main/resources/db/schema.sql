create table label(
    id bigint auto_increment primary key,
    name varchar(128) not null,
    code varchar(128) not null,
    parent_code varchar(128),
    status tinyint not null default 0,
    auth tinyint not null default 0,
    description varchar(1024),
    creator varchar(128) not null,
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp,
    unique (parent_code, name),
    unique (code)
);

create table user(
    id bigint auto_increment primary key,
    username varchar(128) not null,
    password varchar(128) not null,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp,
    unique(username)
);

create table role(
    id bigint auto_increment primary key,
    code varchar(128) not null,
    name varchar(128) not null,
    description varchar(258) not null,
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp,
    unique (code),
    unique (name)
);

create table operation(
    id bigint auto_increment primary key,
    code varchar(128) not null,
    name varchar(128) not null,
    description varchar(256) not null,
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp,
    unique (code),
    unique (name)
);

create table user_role(
    id bigint auto_increment primary key,
    username varchar(128) not null,
    role_code varchar(128) not null,
    create_time timestamp not null default current_timestamp,
    unique (username, role_code)
);

create table role_operation(
    id bigint auto_increment primary key,
    role_code varchar(128) not null,
    operation_code varchar(128) not null,
    create_time timestamp not null default current_timestamp,
    unique (role_code, operation_code)
);

create table user_label(
    id bigint auto_increment primary key,
    username varchar(128) not null,
    label_code varchar(128) not null,
    create_time timestamp not null default current_timestamp,
    unique (username, label_code)
)