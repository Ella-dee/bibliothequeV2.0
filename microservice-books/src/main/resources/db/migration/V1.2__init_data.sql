------------------------------------------------------------
-- Tables: categories
-- init
------------------------------------------------------------
insert into categories (name) values ('fantastique'), ('science-fiction'), ('jeunesse'), ('policier'), ('classique'), ('BD'), ('manga');

------------------------------------------------------------
-- Tables: authors
-- init
------------------------------------------------------------
insert into authors (birth_date, first_name, last_name, nationality) values ('03/01/1892', 'J.R.R.', 'Tolkien', 'Britannique');
insert into authors (birth_date, first_name, last_name, nationality) values ('31/07/1965', 'J.K.', 'Rowling', 'Britannique');
insert into authors (birth_date, first_name, last_name, nationality) values ('19/02/1976', 'Maxime', 'Chattam', 'Française');

------------------------------------------------------------
-- Tables: books
-- init
------------------------------------------------------------

insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Gallimard-Jeunesse', null, '23/08/2007', '9782070612888', 'Le seigneur des anneaux - Volume 1 - La fraternité de l''anneau','Dans les vertes prairies de la Comté, les Hobbits vivent en paix jusqu''au jour fatal où l''un d''entre eux, au cours de ses voyages, entre en possession de l''Anneau unique, aux immenses pouvoirs. Pour le reconquérir, Sauron, le seigneur ténébreux, déchaîne toutes les forces du mal.', 'https://zupimages.net/up/19/30/sapb.jpg', 1, 1);
insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Pocket', 767, '08/09/2017', '9782266282413', 'Le seigneur des anneaux - Volume 3 - Le retour du roi', 'Le royaume de Gondor s''arme contre Sauron, seigneur des ténèbres, qui veut asservir tous les peuples libres, hommes et elfes, nains et hobbits. Mais la vaillance des soldats de Minas Tirith ne peut désormais plus rien contre la puissance maléfique de Mordor. Nouvelle traduction. ', 'https://zupimages.net/up/19/30/t6b5.jpg', 1, 1);
insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Folio Junior', 311, '12/10/2017', '9782070584628', 'Harry Potter - Volume 1 - Harry Potter à l''école des sorciers', null, 'https://zupimages.net/up/19/30/vp5t.jpg', 2, 3);
insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Folio Junior', 355, '12/10/2017', '9782070584642', 'Harry Potter - Volume 2 - Harry Potter et la chambre des secrets', 'De retour chez les Dursley, le temps des vacances,Harry Potter ne souhaite qu''une chose, pouvoir retourner dès que possible à Poudlard,l ''école de sorcellerie. Après une rentrée en voiture volante,Harry doit déjouer une malédiction qui frappe les élèves de Poudlard et qui serait liée à une ancienne chambre secrète.', 'https://zupimages.net/up/19/30/zhgb.jpg',2, 3);
insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Gallimard-Jeunesse', null, '09/10/1998', '9782070518425', 'Harry Potter - Volume 1 - Harry Potter à l''école des sorciers', 'Orphelin vivant chez son oncle et sa tante qui ne l''aiment guère, Harry découvre qu''il est magicien. Il voit son existence bouleversée par l''arrivée d''un géant, Hagrid, qui l''emmène à l''école pour sorciers de Poudlard.','https://zupimages.net/up/19/30/v93c.jpg',2, 3);
insert into books (edition, pages, release_date, ref, title, synopsis, image, id_author, id_category) values ('Pocket', null, '15/05/2003', '9782266127035', 'L''âme du mal', 'En Oregon, l''inspecteur Brolin et Juliette une étudiante en psychologie enquêtent sur un secret qui met leur vie en péril : un tueur abattu semble avoir ressuscité. Il mutile ses victimes de manière rituelle, en laissant des indices tirés de la Bible noire. Les investigations des spécialistes de la médecine légale et de la police scientifique, que l''auteur connaît bien, sont décrites en détail. ','https://zupimages.net/up/19/30/ua80.jpg',3, 4);

------------------------------------------------------------
-- Tables: borrowing_types
-- init
------------------------------------------------------------

insert into borrowing_types(type) values ('en cours'), ('terminé'), ('prolongé'), ('en retard');

------------------------------------------------------------
-- Tables: borrowings
-- init
------------------------------------------------------------

insert into borrowings(date_borrowed, date_returned, reminder_mail, id_book, id_type, id_user) values ('13/07/2018', '12/08/2018', false, 1, 1, 1);
insert into borrowings(date_borrowed, date_returned, reminder_mail, id_book, id_type, id_user) values ('13/07/2019', null, false, 3, 3, 1);

