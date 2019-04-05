package com.epam.training.toto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    public LocalDate getValidDate() {
        ConsoleReader consoleReader = new ConsoleReader();
        while (true) {
            try {
                LocalDate date = consoleReader.readDate();
                if (date.getYear() > 1998 && date.getYear() < 2016) {
                    return date;
                } else {
                    System.out.println("Sorry, we only have records between 1998 and 2015.");
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                System.out.println("The date you have entered is not valid!" +
                        " Please remember to use this format: 2000.01.01.");
            }
        }
    }

    public String getValidOutcomes() {
        ConsoleReader consoleReader = new ConsoleReader();
        while (true) {
            try {
                String enteredOutcomes = consoleReader.readOutcomes();
                if (enteredOutcomes.length() == 14 && !(areInvalidCharacters(enteredOutcomes))) {
                    return enteredOutcomes;
                } else {
                    System.out.println("The outcomes you have entered are not valid!" +
                            "You have to enter 14 outcomes, each have to be either 1, 2 or X");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean areInvalidCharacters(String enteredOutcomes) {
        for (int i = 0; i < enteredOutcomes.length(); i++) {
            char c = enteredOutcomes.toUpperCase().charAt(i);
            if ((c != '1') && (c != '2') && (c != 'X')) {
                return true;
            }
        }
        return false;
    }
}
