package com.epam.training.toto.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserGivenInputValidator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");


    public LocalDate validateDate(String date) throws IllegalArgumentException, DateTimeParseException {
        LocalDate localDate = LocalDate.parse(date, FORMATTER);
        if ((localDate.isAfter(LocalDate.of(1997, 12,31)))
                && localDate.isBefore(LocalDate.of(2015, 10, 30))) {
            return localDate;
        } else {
            throw  new IllegalArgumentException("The entered date has to be between 1998 and 2015.10.29. !");
        }
    }

    public boolean validateOutcomes(String outcomes) throws IllegalArgumentException{
        if (outcomes.length() == 14 && outcomes.matches("[12xX]*|.") ) {
            return true;
        } else {
            throw new IllegalArgumentException("You have to enter 14 characters, each ot them has to be either 1, 2, or X!");
        }
    }
}
