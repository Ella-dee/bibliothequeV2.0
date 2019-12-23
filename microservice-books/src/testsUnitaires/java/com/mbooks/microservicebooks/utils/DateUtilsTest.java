package com.mbooks.microservicebooks.utils;

import com.mbooks.microservicebooks.exceptions.InvalidDateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {
    private String dateString;

    @Before
    public void setUp() throws Exception {
        dateString = "24/12/1984";

    }

    @Test(expected = Test.None.class)
    public void checkDate() {
        DateUtils.checkDate(dateString);
    }
    @Test(expected = InvalidDateException.class)
    public void checkDateFail() {
        dateString = "31/02/2478";
        DateUtils.checkDate(dateString);
    }

    @Test(expected = Test.None.class)
    public void convertStringToDateFormat() {
        DateUtils.convertStringToDateFormat(dateString);
    }
    @Test(expected = ParseException.class)
    public void convertStringToDateFormatFail() {
        DateUtils.convertStringToDateFormat("");
    }

    @Test(expected = Test.None.class)
    public void convertToLocalDateViaInstant() {
        Date date = DateUtils.convertStringToDateFormat(dateString);
        DateUtils.convertToLocalDateViaInstant(date);
    }

}