package com.mbooks.microservicebooks.dao;

import com.mbooks.microservicebooks.model.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WaitingListDao extends JpaRepository<WaitingList, Integer> {
    List<WaitingList> findWaitingListByIdUser(Integer userId);
    List<WaitingList> findWaitingListByBook_Id(Integer bookId);

}
