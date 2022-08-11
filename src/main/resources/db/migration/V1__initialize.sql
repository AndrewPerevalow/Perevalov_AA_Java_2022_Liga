create table users (
    id           bigserial
        primary key,
    name         varchar(255),
    surname      varchar(255),
    login        varchar(255),
    email        varchar(255),
    password     varchar(255),
    is_exist     boolean,
    phone_number varchar(255)
);

insert into users (id, email, is_exist, login, name, password, phone_number, surname)
values (1, 'operfirst@mail.ru', true, 'oper1', 'Sergey', '$2a$12$XJeKD31lP4Cf9iERuHXKo.9WVGPlOtxngfRxpQAcNJOJxjPT6FrMS',
        '89132134576', 'Sergeev'),
       (2, 'opersecond@mail.ru', true, 'oper2', 'Vadim', '$2a$12$My0o0jAtrM/TaVrfgxLrnOyzw/VWI5Vlo7qq15YwAr.W1r3ogZ3My',
        '89214568734', 'Vadimov'),
       (3, 'operthird@mail.ru', true, 'oper3', 'Petr', '$2a$12$ZQoj4oH/nJQH2WYXxuyJpuz/RtahcrGjUW52j0SpjEfYtBAYt9qEO',
        '89114764354', 'Petrov'),
       (4, 'adminfirst@mail.ru', true, 'admin1', 'Alisa','$2a$12$crOjyaB/jcg5465e8goheeOVTdQf1jJKwlKM/D1m6du.r20AU./Tm',
        '89117562312', 'Avdeeva');

create table roles (
    id   bigint not null
        primary key,
    name varchar(255)
);

insert into roles (id, name)
values (1, 'ROLE_USER'),
       (2, 'ROLE_OPERATOR'),
       (3, 'ROLE_ADMIN');

create table users_roles (
    user_id bigint not null
        constraint fk1_roles_users
            references users,
    role_id bigint not null
        constraint fk2_roles_users
            references roles,
    constraint users_roles_pk
        primary key (user_id, role_id)
);

insert into users_roles (user_id, role_id)
values (1, 2),(2, 2),(3, 2),(4, 3);


create table boxes (
    id             bigserial
        primary key,
    name           varchar(255),
    ratio          double precision,
    work_from_time time,
    work_to_time   time,
    user_id        bigint
        constraint fk_boxes_users
            references users
);

insert into boxes (id, name, ratio, work_from_time, work_to_time, user_id)
values (1, 'box1', 1, '08:00:00', '20:00:00', 1),
       (2, 'box2', 0.9, '05:00:00', '18:00:00', 2),
       (3, 'box3', 1.1, '00:00:00', '23:59:00', 3);

create table bookings (
    id               bigserial
        primary key,
    date             date,
    start_time       time,
    end_time         time,
    status           varchar(255),
    box_id           bigint
        constraint fk1_bookings_boxes
            references boxes,
    user_id          bigint
        constraint fk2_bookings_users
            references users,
    user_is_come     boolean default false not null,
    discount_percent double precision default 0.0 not null,
    total_price      double precision
);

create index bookings_date_index
    on bookings (date);

create index bookings_start_time_index
    on bookings (start_time);

create index bookings_status_index
    on bookings (status);

create table operations (
    id                bigserial
        constraint services_pkey
            primary key,
    name              varchar(255),
    lead_time_minutes integer,
    price             double precision
);

insert into operations (id, lead_time_minutes, name, price)
values (1, 15, 'service1', 300),
       (2, 30, 'service2', 600),
       (3, 35, 'service3', 450),
       (4, 60, 'service4', 1000),
       (5, 45, 'service5', 2000),
       (6, 30, 'washing', 300),
       (7, 15, 'light washing', 150);

create table bookings_operations (
    booking_id   bigint not null
        constraint fk_operations_bookings
            references bookings,
    operation_id bigint not null
        constraint fk2_operations_bookings
            references operations,
    constraint bookings_operations_pk
        primary key (booking_id, operation_id)
);

create table discounts (
    id    bigint not null
        primary key,
    name  varchar(255),
    value double precision
);

insert into discounts (id, name, value)
values (1, 'min', 5),
       (2, 'max', 15);