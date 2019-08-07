------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------


------------------------------------------------------------
-- Table: mailing_types
------------------------------------------------------------
CREATE TABLE public.mailing_types(
                                     id     SERIAL NOT NULL ,
                                     type   VARCHAR (100) NOT NULL  ,
                                     CONSTRAINT mailing_types_PK PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: mailings
------------------------------------------------------------
CREATE TABLE public.mailings(
                               id            SERIAL NOT NULL ,
                               sent_date     VARCHAR (50) NOT NULL,
                               id_user       INTEGER  NOT NULL,
                               email_user    VARCHAR (100) NOT NULL,
                               id_book       INT  NOT NULL,
                               title_book    VARCHAR (200) NOT NULL,
                               id_type       INT,
                               CONSTRAINT mailings_PK PRIMARY KEY (id),
    CONSTRAINT mailings_mailing_types0_FK FOREIGN KEY (id_type) REFERENCES public.mailing_types(id)
)WITHOUT OIDS;






