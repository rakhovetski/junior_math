INSERT INTO users(id, username, email, lastname, firstname, patronymic, role)
VALUES (1, 'username', 'email@email.com',
        'lastname', 'firstname', 'patronymic', 'TEACHER');

INSERT INTO users(id, username, email, lastname, firstname, patronymic, role)
VALUES (2, 'username-two', 'email-two@email.com',
        'lastname', 'firstname', 'patronymic', 'TEACHER');


INSERT INTO teacher(id) VALUES (1);
INSERT INTO teacher(id) VALUES (2);


INSERT INTO subject(id, name) VALUES (1, 'Algebra');
INSERT INTO subject(id, name) VALUES (2, 'IT');
INSERT INTO subject(id,name) VALUES (3, 'Geometry');


INSERT INTO task(class, topic, condition, answer, solve, created_by, teacher_id, subject_id)
VALUES(9, 'topicAl', 'conditionAl', 'answerAl', 'solveAl', 'username', 1, 1);

INSERT INTO task(class, topic, condition, answer, solve, created_by, teacher_id, subject_id)
VALUES(10, 'topicIT1', 'conditionIT1', 'answerIT1', 'solveIT1', 'username', 1, 2);

INSERT INTO task(class, topic, condition, answer, solve, created_by, teacher_id, subject_id)
VALUES(10, 'topicIT2', 'conditionIT2', 'answerIT2', 'solveIT2', 'username', 1, 2);


INSERT INTO room(code, name, class, created_by)
VALUES ('ASD123', 'room-name1', 9, 'username');

INSERT INTO room(code, name, class, created_by)
VALUES ('ASD456', 'room-name2', 9, 'username');

INSERT INTO room(code, name, class, created_by)
VALUES ('SDF432', 'room-name3', 10, 'username');

INSERT INTO room(code, name, class, created_by)
VALUES ('ZXCDDS', 'room-name4', 11, 'username');