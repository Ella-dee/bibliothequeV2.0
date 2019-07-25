package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {

        
}
