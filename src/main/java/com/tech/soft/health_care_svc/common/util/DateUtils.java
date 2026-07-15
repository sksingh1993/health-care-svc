package com.tech.soft.health_care_svc.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateUtils {

    private DateUtils() {
    }

    public static int calculateDays(LocalDate fromDate, LocalDate toDate) {
        return (int) ChronoUnit.DAYS.between(fromDate, toDate) + 1;
    }

    public static String convertDate(LocalDate date){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return date.format(dateTimeFormatter);

    }
}