package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.BorrowingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowingTypeDao extends JpaRepository<BorrowingType, Integer> {

        
}
