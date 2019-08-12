package com.mbooks.microservicebooks.utils;

import com.mbooks.microservicebooks.exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class DateUtils {
    /**
     * <p>method checks if date is right format, and if it is valid</p>
     * @param date
     */
    public static void checkDate(String date){
        //date regex for valid format dd-mm-yyyy, accounts for number of days in months including february
        String datecheckRegex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (!date.matches(datecheckRegex)) {
            throw new InvalidDateException("Le format de la date est invalide, merci d'entrer un format dd/mm/yyyy");
        }
    }
    /**
     * <p>Parses string to LocalDate</p>
     * @param dateToConvert
     * @return localDate
     */
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
