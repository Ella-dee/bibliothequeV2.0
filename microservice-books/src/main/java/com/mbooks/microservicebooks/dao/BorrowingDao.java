package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findBorrowingByIdUser (Integer id);
        
}
