package com.example.finalprojectaozcann;

import com.example.finalprojectaozcann.utils.DateUtil;

import java.time.LocalDate;
import java.time.Month;

public class maintest {
    public static void main(String[] args) {

        System.out.println(DateUtil.dateFormatLocalDateNowToString());
        System.out.println( DateUtil.dateFormatStringToLocalDate("02/06/2022"));
        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().isEqual( DateUtil.dateFormatStringToLocalDate("02/06/2022")));
        boolean equals = DateUtil.dateFormatStringToLocalDate("02/06/2022").equals(LocalDate.now());
        System.out.println(equals);
        LocalDate last=LocalDate.of ( 2015, Month.DECEMBER, 31);
        LocalDate now= LocalDate.of ( 2016, Month.JANUARY, 1);
        java.time.Period period=java.time.Period.between ( last, now);
        System.out.println ( period.getDays () );

        System.out.println(DateUtil.checkTransferDate("01/05/2022"));
    }
}
