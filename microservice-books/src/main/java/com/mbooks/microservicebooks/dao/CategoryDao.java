package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findCategoryById(Integer id);

        
}
