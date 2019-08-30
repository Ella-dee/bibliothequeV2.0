package com.musers.microserviceusers.batch;

import com.musers.microserviceusers.dao.UserDao;
import com.musers.microserviceusers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>Class that checks age of token if any and erases them or not accordingly</h1>
 */
@Component
public class ScheduledTasks {
    @Autowired
    private UserDao userDao;

    @Scheduled(cron="0 0 23 * * MON-SUN") //Fire at 11pm every day
    public void checkTokens(){
        List<User> allUsers = userDao.findAll();
        List<User> usersWithToken = new ArrayList<>();

        for (User u:allUsers){
            if (u.getResetToken() != null){
                usersWithToken.add(u);
            }
        }

        for (User u : usersWithToken){
            String tokenDate = u.getTokenDate();
            Date date = new Date();
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(tokenDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            LocalDate tokenCreationDate = convertToLocalDateViaInstant(date);
            ZoneId zone = ZoneId.of("Europe/Paris");
            LocalDate today = LocalDate.now(zone);

            if (tokenCreationDate.isAfter(today)) {
                u.setResetToken(null);
                u.setTokenDate(null);
                userDao.save(u);
            }
        }
    }
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
