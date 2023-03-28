create table if not exists person (
 id serial primary key not null,
 username varchar(2000),
 password varchar(2000)
);