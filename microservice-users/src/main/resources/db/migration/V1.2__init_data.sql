------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------



------------------------------------------------------------
-- Tables: roles
-- init
------------------------------------------------------------
insert into roles (role_name) values ('ADMIN'), ('USER');

------------------------------------------------------------
-- Table: users
------------------------------------------------------------
insert into users (username, first_name, last_name, email, password, id_role) values ('elojito', 'elodie','ollivier','hellojito@gmail.com', MD5('backstreet'), 2);
insert into users (username, first_name, last_name, email, password, id_role) values ('admin', 'admin','admin','elojito@live.fr', MD5('backstreet'), 1);



