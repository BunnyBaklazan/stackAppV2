package com.example.stackapp.model;

import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.Period.between;

public class SampleUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

    public static String calcPeriod(String from, String end) {
        Period period = between(parse(from, formatter), parse(end, formatter));
        return period.getYears() + " years";
    }
}
