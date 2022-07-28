create table comments
(
    id      serial
        constraint comments_pk
            primary key,
    content varchar,
    id_task int not null
        constraint fk_id_task
            references tasks (id)
);

insert into comments (id, content, id_task)
values (1, 'Комментарий 1', 1),
       (2, 'Комментарий 2', 1),
       (3, 'Комментарий 3', 2),
       (4, 'Комментарий 4', 3),
       (5, 'Комментарий 5', 5),
       (6, 'Комментарий 6', 5),
       (7, 'Комментарий 7', 5),
       (8, 'Комментарий 8', 7),
       (9, 'Комментарий 9', 8),
       (10, 'Комментарий 10', 9),
       (11, 'Комментарий 11', 11),
       (12, 'Комментарий 12', 11);

create table projects
(
    id          serial
        constraint projects_pk
            primary key,
    header      varchar,
    description varchar
);

insert into projects (id, header, description)
values (1, 'Проект 1', 'Разработка игры'),
       (2, 'Проект 2', 'Разработка сайта');

alter table tasks
    add id_project int not null default 1,
    add constraint fk_id_project
        foreign key (id_project) references projects (id);

update tasks
set id_project = 2
where id = 1
   or id = 2
   or id = 5
   or id = 8
   or id = 9
   or id = 10
   or id = 11
   or id = 13
   or id = 14
   or id = 15;

alter table users
    add surname varchar,
    add patronymic varchar,
    add login varchar,
    add email varchar,
    add password varchar;


create table users_projects
(
    id_user    int not null
        constraint users_projects_fk1
            references users (id),
    id_project int not null
        constraint users_projects_fk2
            references projects (id),
    constraint users_projects_pk
        primary key (id_user, id_project)
);

insert into users_projects (id_user, id_project)
values (1, 2),
       (2, 2),
       (3, 1),
       (4, 1),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 1),
       (10, 2),
       (11, 2);

update users as u
set surname    = u2.surname,
    patronymic = u2.patronymic,
    login      = u2.login,
    email      = u2.email,
    password   = u2.password
    from (
values  (1, 'Петр', 'Фамилия1', 'Отчество1', 'Логин1', 'Почта1@mail.com', 'Пароль1'),
        (2, 'Иван', 'Фамилия2', 'Отчество2', 'Логин2', 'Почта2@mail.com', 'Пароль2'),
        (3, 'Дарья', 'Фамилия3', 'Отчество3', 'Логин3', 'Почта3@mail.com', 'Пароль3'),
        (4, 'Андрей', 'Фамилия4', 'Отчество4', 'Логин4', 'Почта4@mail.com', 'Пароль4'),
        (5, 'Екатерина', 'Фамилия5', 'Отчество5', 'Логин5', 'Почта5@mail.com', 'Пароль5'),
        (6, 'Александр', 'Фамилия6', 'Отчество6', 'Логин6', 'Почта6@mail.com', 'Пароль6'),
        (7, 'Алексей', 'Фамилия7', 'Отчество7', 'Логин7', 'Почта7@mail.com', 'Пароль7'),
        (8, 'Татьяна', 'Фамилия8', 'Отчество8', 'Логин8', 'Почта8@mail.com', 'Пароль8'),
        (9, 'Валентина', 'Фамилия9', 'Отчество9', 'Логин9', 'Почта9@mail.com', 'Пароль9'),
        (10, 'Алина', 'Фамилия10', 'Отчество10', 'Логин10', 'Почта10@mail.com', 'Пароль10'),
        (11, 'Сергей', 'Фамилия11', 'Отчество11', 'Логин11', 'Почта11@mail.com', 'Пароль11')
        ) as u2 (id, name, surname, patronymic, login, email, password)
where u2.id = u.id;