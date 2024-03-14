INSERT INTO users(id, username, email, lastname, firstname, patronymic)
VALUES (1, 'username', 'email@email.com',
        'lastname', 'firstname', 'patronymic');

INSERT INTO teacher(id) VALUES (1);

INSERT INTO subject(id, name) VALUES (1, 'Algebra');
INSERT INTO subject(id, name) VALUES (2, 'IT');
INSERT INTO subject(id,name) VALUES (3, 'Geometry');

INSERT INTO task(id, class, topic, condition, answer, solve, teacher_id, subject_id)
VALUES(1, 9, 'topicAl', 'conditionAl', 'answerAl', 'solveAl',
       1, 1);
INSERT INTO task(id, class, topic, condition, answer, solve, teacher_id, subject_id)
VALUES(2, 10, 'topicIT1', 'conditionIT1', 'answerIT1', 'solveIT1',
       1, 2);
INSERT INTO task(id, class, topic, condition, answer, solve, teacher_id, subject_id)
VALUES(3, 10, 'topicIT2', 'conditionIT2', 'answerIT2', 'solveIT2',
       1, 2);