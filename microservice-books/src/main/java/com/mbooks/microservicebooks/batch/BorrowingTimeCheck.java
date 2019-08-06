package com.mbooks.microservicebooks.batch;

import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.dao.BorrowingTypeDao;
import com.mbooks.microservicebooks.model.Borrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class BorrowingTimeCheck {
    @Autowired
    private BorrowingDao borrowingDao;
    @Autowired
    private BorrowingTypeDao borrowingTypeDao;

    public void checkAndSet(){
        List<Borrowing> borrowings = borrowingDao.findAll();
        for(Borrowing borrowing:borrowings){
            String limit = borrowing.getLimitDate();
            Date date = new Date();
            try{
                date=new SimpleDateFormat("dd/MM/yyyy").parse(limit);
            }catch (ParseException e){
                e.printStackTrace();
            }

            LocalDate limitDate = convertToLocalDateViaInstant(date);
            ZoneId zone = ZoneId.of("Europe/Paris");
            LocalDate today = LocalDate.now(zone);

            if(limitDate.isAfter(today) && borrowing.getRenewed() == true){
                borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(4));
                //send Mail en retard
            }
           if(limitDate.isAfter(today) && borrowing.getRenewed() == false){
                borrowing.setBorrowingType(borrowingTypeDao.findBorrowingTypeById(4));
                //send Mail vous pouvez prolonger de 4 semaines
            }
            //TODO ajouter mail
            //TODO ajouter nbr prolongation dans borrowing


        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
