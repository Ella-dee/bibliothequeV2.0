package com.mbooks.microservicebooks.batch;

import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.dao.BorrowingTypeDao;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <h1>Class that checks and eventually modifies a borrowing status</h1>
 */
@Component
public class ScheduledTasks {
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;

    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing limit dates are passed, and if so changes the status to "late" </p>
     */
    @Scheduled(cron="0 0 0 * * *") //Fire at 0am everyday
    public void checkAndSet(){
        List<Borrowing> borrowings = borrowingDao.findAll();
        for(Borrowing borrowing:borrowings){
            String limit = borrowing.getLimitDate();
            Date date = new Date();
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(limit);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate limitDate = DateUtils.convertToLocalDateViaInstant(date);
            ZoneId zone = ZoneId.of("Europe/Paris");
            LocalDate today = LocalDate.now(zone);

            if (limitDate.isBefore(today) && borrowing.getReturned() == null) {
                borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(4));
                borrowingDao.save(borrowing);
            }
        }
    }

}

