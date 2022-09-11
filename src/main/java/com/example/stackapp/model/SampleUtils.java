package com.example.stackapp.model;

import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.Period.between;

public class SampleUtils {

    private static final String SEPARATOR = ".";

    public static String calcPeriod(String from, String end) {
        int fromDate = Integer.parseInt(from.substring(from.lastIndexOf(SEPARATOR) + 1));
        int endDate = Integer.parseInt(end.substring(from.lastIndexOf(SEPARATOR) + 1));
        return endDate - fromDate + " years";
    }
}
