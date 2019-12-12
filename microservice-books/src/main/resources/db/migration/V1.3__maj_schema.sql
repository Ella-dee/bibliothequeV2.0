------------------------------------------------------------
--        Script Postgre
------------------------------------------------------------


------------------------------------------------------------
-- Table: waitinglist
------------------------------------------------------------
CREATE TABLE public.waitinglist(
                                  id                   SERIAL NOT NULL ,
                                  id_user              INTEGER  NOT NULL ,
                                  user_pos             INTEGER,
                                  id_book              INT  NOT NULL ,
                                  CONSTRAINT waitinglist_PK PRIMARY KEY (id)

    ,CONSTRAINT waitinglist_books_FK FOREIGN KEY (id_book) REFERENCES public.books(id)
)WITHOUT OIDS;
