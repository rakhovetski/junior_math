CREATE TABLE IF NOT EXISTS users
(
    id               serial
    primary key,
    created_at       timestamp   default now()                     not null,
    updated_at       timestamp   default now(),
    username         varchar(128)               unique             not null,
    email            varchar(128)               unique             not null,
    lastname         varchar(128)                                  not null,
    firstname        varchar(128)                                  not null,
    patronymic       varchar(128),
    role             varchar(64) default 'student'                 not null
);


CREATE TABLE IF NOT EXISTS room
(
    id               serial
    primary key,
    code             varchar(10)                 unique            not null,
    name             varchar(256)                                  not null,
    class            smallint                                      not null,
    created_by       varchar(128)                                  not null,
    created_at       timestamp   default now()                     not null
);


CREATE TABLE IF NOT EXISTS users_room
(
    id               serial
    primary key,
    users_id         int
    references       users(id),
    room_id          int
    references       room(id)
);


CREATE TABLE IF NOT EXISTS characters
(
    id               serial
    primary key,
    name             varchar(128)                                  not null
);


CREATE TABLE IF NOT EXISTS student
(
    id               int
    primary key      references users(id),
    experience       int         default 0                         not null,
    level            int         default 1                         not null,
    completed_tasks  int         default 0                         not null,
    character_id     int
    references       characters(id)
);


CREATE TABLE IF NOT EXISTS teacher
(
    id               int
    primary key      references users(id)
);


CREATE TABLE IF NOT EXISTS subject
(
    id               serial
    primary key,
    name             varchar(128)
);


CREATE TABLE IF NOT EXISTS task
(
    id               serial
    primary key,
    class            smallint,
    topic            varchar(256)                                  not null,
    condition        text                                          not null,
    answer           varchar(128)                                  not null,
    solve            text                                          not null,
    created_by       varchar(128)                                  not null,
    created_at       timestamp   default now()                     not null,
    updated_at       timestamp   default now(),
    teacher_id       int
    references       teacher(id),
    subject_id       int
    references       subject(id)
);


CREATE TABLE IF NOT EXISTS test
(
    id               serial
    primary key,
    name             varchar(256)                                  not null,
    created_at       timestamp   default now()                     not null,
    started_at       timestamp,
    room_id          int
    references       room(id)
);


CREATE TABLE IF NOT EXISTS task_test
(
    id               serial
    primary key,
    task_id          int
    references       task(id),
    test_id          int
    references       test(id),
    student_answer   varchar(128),
    student_id       int
);