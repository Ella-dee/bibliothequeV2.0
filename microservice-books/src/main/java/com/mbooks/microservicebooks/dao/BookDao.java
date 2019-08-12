package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookDao extends JpaRepository<Book, Integer> {
    List<Book> findBooksByAuthor_Id(Integer id);
    List<Book> findBooksByCategory_Id(Integer id);
    Book findBookById(Integer id);

    List<Book> findBooksByTitleContainingIgnoreCase (String title);
}
