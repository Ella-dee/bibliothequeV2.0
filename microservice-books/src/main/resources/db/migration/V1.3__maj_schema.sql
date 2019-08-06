------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------

------------------------------------------------------------
-- Table: borrowings
------------------------------------------------------------

alter table borrowings add column renewed boolean not null default false;

update borrowings
set renewed = true
where id = 1;


