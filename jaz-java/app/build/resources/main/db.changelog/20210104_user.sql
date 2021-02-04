 create table "user"(
        id       bigserial not null
        constraint users_pkey
        primary key,
        username varchar,
        password varchar,
        role     varchar
);
