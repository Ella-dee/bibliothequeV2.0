
------------------------------------------------------------
-- Table: mailings
------------------------------------------------------------
truncate table public.mailings;
alter table public.mailings drop constraint mailings_mailing_types0_FK;
alter table public.mailings drop column id_borrowing;
alter table public.mailings drop column id_type;

