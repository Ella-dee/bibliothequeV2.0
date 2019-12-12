package com.mmailing.microservicemailing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateUtils {

    /**
     * <p>Parses date to LocalDate</p>
     *
     * @param dateToConvert
     * @return localDate
     */
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    /**
     * <p>Gets LocalDate for today</p>
     * @return localDate
     */
    public static LocalDate theDateToday(){
        ZoneId zone = ZoneId.of("Europe/Paris");
        LocalDate today = LocalDate.now(zone);
        return today;
    }
    /**
     *  <p>Parses string to Date</p>
     * @param dateStr
     * @return
     */
    public static Date convertStringToDateFormat(String dateStr) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * <p>Parses a LocalDate to string</p>
     * @return string
     */
    public static String localDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
