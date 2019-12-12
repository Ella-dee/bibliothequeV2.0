
------------------------------------------------------------
-- Table: mailings
------------------------------------------------------------
update table public.mailings drop constraint mailings_mailing_types0_FK;
update table public.mailings drop column id_borrowing;
update table public.mailings drop column id_type;

