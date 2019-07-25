------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------



------------------------------------------------------------
-- Table: users
------------------------------------------------------------
DROP TABLE IF EXISTS public.users;

CREATE TABLE public.users(
                               id            SERIAL NOT NULL ,
                               first_name   VARCHAR (50) NOT NULL ,
                               last_name     VARCHAR (50) NOT NULL ,
                               email   VARCHAR (100)   ,
                               password   VARCHAR (100)   ,
                               CONSTRAINT authors_PK PRIMARY KEY (id)
)WITHOUT OIDS;

