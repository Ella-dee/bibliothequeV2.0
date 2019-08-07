package com.mbooks.microservicebooks.batch;

import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.dao.BorrowingTypeDao;
import com.mbooks.microservicebooks.model.Borrowing;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskOne {
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;

    /**
     * <p>method called when application is ran</p>
     *  <p>checks if borrowing limit dates are passed, and if so changes the status to "late" </p>
     */
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

                LocalDate limitDate = convertToLocalDateViaInstant(date);
                ZoneId zone = ZoneId.of("Europe/Paris");
                LocalDate today = LocalDate.now(zone);


            if (limitDate.isBefore(today) && borrowing.getReturned() == null) {
                borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(4));
                borrowingDao.save(borrowing);
            }


        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

/**
<h2>Tasklet for task one</h2>
 <p>checks if borrowing limit dates are passed, and if so changes the status to "late" </p>
**/
/*
public class TaskOne implements Tasklet {

    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("MyTaskOne start..");

        List<Borrowing> borrowings = borrowingDao.findAll();
        for(Borrowing borrowing:borrowings) {
            String limit = borrowing.getLimitDate();
            Date date = new Date();
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(limit);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate limitDate = convertToLocalDateViaInstant(date);
            ZoneId zone = ZoneId.of("Europe/Paris");
            LocalDate today = LocalDate.now(zone);

            if (limitDate.isAfter(today) && borrowing.getReturned() == null) {
                borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(4));
                borrowingDao.save(borrowing);
            }
        }
        System.out.println("MyTaskOne done..");
        return RepeatStatus.FINISHED;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
*/
