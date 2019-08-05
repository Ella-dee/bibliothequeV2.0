------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------



------------------------------------------------------------
-- Table: authors
------------------------------------------------------------
CREATE TABLE public.authors(
                               id            SERIAL NOT NULL ,
                               birth_date    VARCHAR (50) DEFAULT NULL ,
                               first_name   VARCHAR (50) NOT NULL ,
                               last_name     VARCHAR (50) NOT NULL ,
                               nationality   VARCHAR (50)  DEFAULT NULL ,
                               CONSTRAINT authors_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: categories
------------------------------------------------------------
CREATE TABLE public.categories(
                                  id     SERIAL NOT NULL ,
                                  name   VARCHAR (50) NOT NULL  ,
                                  CONSTRAINT categories_PK PRIMARY KEY (id) ,
                                  CONSTRAINT categories_AK UNIQUE (name)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: books
------------------------------------------------------------
CREATE TABLE public.books(
                             id              SERIAL NOT NULL ,
                             edition         VARCHAR (50) NOT NULL ,
                             pages           INT  DEFAULT NULL ,
                             ref             VARCHAR (50) NOT NULL ,
                             release_date    VARCHAR (50) NOT NULL ,
                             synopsis        VARCHAR (600) DEFAULT NULL,
                             title           VARCHAR (200) NOT NULL ,
                             image           VARCHAR (200) DEFAULT NULL,
                             id_author      INT  NOT NULL ,
                             id_category   INT  NOT NULL  ,
                             CONSTRAINT books_PK PRIMARY KEY (id)

    ,CONSTRAINT books_authors_FK FOREIGN KEY (id_author) REFERENCES public.authors(id)
    ,CONSTRAINT books_categories0_FK FOREIGN KEY (id_category) REFERENCES public.categories(id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: borrowing_types
------------------------------------------------------------
CREATE TABLE public.borrowing_types(
                                       id     SERIAL NOT NULL ,
                                       type   VARCHAR (100) NOT NULL  ,
                                       CONSTRAINT borrowing_types_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: borrowings
------------------------------------------------------------
CREATE TABLE public.borrowings(
                                  id                   SERIAL NOT NULL ,
                                  date_borrowed        VARCHAR (50) NOT NULL ,
                                  date_limit           VARCHAR (50) NOT NULL ,
                                  date_returned        VARCHAR (50) DEFAULT NULL ,
                                  reminder_mail        BOOLEAN  NOT NULL DEFAULT FALSE,
                                  id_user              INTEGER  NOT NULL ,
                                  id_book              INT  NOT NULL ,
                                  id_type              INT  NOT NULL  ,
                                  CONSTRAINT borrowings_PK PRIMARY KEY (id)

    ,CONSTRAINT borrowings_books_FK FOREIGN KEY (id_book) REFERENCES public.books(id)
    ,CONSTRAINT borrowings_borrowing_types0_FK FOREIGN KEY (id_type) REFERENCES public.borrowing_types(id)
)WITHOUT OIDS;


