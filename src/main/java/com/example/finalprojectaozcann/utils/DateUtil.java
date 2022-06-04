package com.example.finalprojectaozcann.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DateUtil {
    public static LocalDate dateFormatStringToLocalDate(String date) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, f);
    }

    public static String dateFormatLocalDateNowToString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Long checkTransferDate(String date) {

        LocalDate transferDate = dateFormatStringToLocalDate(date);
        LocalDate now = LocalDate.now();
        Period period = Period.between(now, transferDate);
        return Long.valueOf(period.getDays());
    }

}
