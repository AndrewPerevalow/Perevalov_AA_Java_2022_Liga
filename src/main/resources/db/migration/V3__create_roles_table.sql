create table roles (
    id   serial
        constraint roles_pk
            primary key,
    name varchar
);

create table users_roles (
    id_user int not null
        constraint users_roles_fk1
            references users (id),
    id_role int not null
        constraint users_roles_fk2
            references roles (id),
    constraint users_roles_pk
        primary key (id_user, id_role)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users_roles (id_user, id_role)
values (1,1),(2,1),(3,1),(4,2),(4,1),(5,1),(6,1),(7,2),(8,2),(9,1),(10,2),(11,2);