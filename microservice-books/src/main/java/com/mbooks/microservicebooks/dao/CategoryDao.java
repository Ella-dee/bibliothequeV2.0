package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findCategoryById(Integer id);

        
}
