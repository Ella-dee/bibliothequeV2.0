package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

}
