
insert into task_list.employee(id, name, position, birthday, gender)
VALUES (1,'Leon', 1, '1989-11-12', 0 ),
       (2,'Alph', 0, '1989-11-12', 0),
       (3,'Vanessa', 2, '1988-10-11', 1),
       (4,'Mark', 3, '1995-07-09', 0),
       (5,'Mira', 4, '1992-06-10', 1);




insert into task_list.task(id, description, deadline, status, level, employee_id)
VALUES (1, 'Count salary', '2023-12-22', 0, 1, 1),
       (2, 'Hire new counter', '2023-12-27', 0, 1, 2),
       (3, 'Write a report', '2023-12-25', 1, 0, 4),
       (4, 'Configure excel', '2023-11-11', 0, 3, 3),
       (5, 'Meet with president', '2023-10-09', 0, 3, 1),
       (6, 'Count taxes', '2023-10-01', 1, 2, 2),
       (7, 'Make copies of documents', '2023-11-11', 0, 2, 3),
       (8, 'Write a claim', '2023-09-22', 0, 1, 5),
       (9, 'Call lawyers', '2023-10-27', 1, 0, 1),
       (10, 'Check documents', '2023-11-28', 0, 1, 5);