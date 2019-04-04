package com.epam.training.toto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validator {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");

    public LocalDate getValidDateFromConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter a date from 1998 - 2015 (the format should be like this: 2000.11.28.):");
            try {
                LocalDate date = LocalDate.parse(scanner.nextLine(), FORMATTER);
                if (date.getYear() > 1998 && date.getYear() < 2016) {
                    scanner.close();
                    return date;
                } else {
                    System.out.println("Sorry, we only have records between 1998 and 2015.");
                }
            } catch (Exception e) {
                System.out.println("The date you have entered is not valid!" +
                        " Please remember to use this format: 2000.01.01.");
            }
        }
    }

    public String getValidOutcomesFromConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter the 14 outcomes (for example: 11xx22x11x22x1):");
            String enteredOutcomes = scanner.nextLine();
            if (enteredOutcomes.length() == 14 && !(areInvalidCharacters(enteredOutcomes))) {
                scanner.close();
                return enteredOutcomes;
            } else {
                System.out.println("The outcomes you have entered are not valid!" +
                        "You have to enter 14 outcomes, each have to be either 1, 2 or X");
            }
        }
    }

    private boolean areInvalidCharacters(String enteredOutcomes) {
        for (int i = 0; i < enteredOutcomes.length(); i++) {
            char c = enteredOutcomes.toUpperCase().charAt(i);
            if (c != '1' && c != '2' && c != 'X') {
                return true;
            }
        }
        return false;
    }
}
