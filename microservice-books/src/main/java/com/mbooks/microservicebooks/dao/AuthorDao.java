package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Author;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {
    List <Author> findAllByLastNameLike (String lastName);
}
