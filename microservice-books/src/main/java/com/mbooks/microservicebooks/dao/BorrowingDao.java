package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {

        
}
