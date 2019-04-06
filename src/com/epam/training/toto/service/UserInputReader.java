package com.epam.training.toto.service;
import com.epam.training.toto.domain.Outcome;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");
    private static Scanner scanner = new Scanner(System.in);


    public LocalDate readDate () throws DateTimeParseException {
        System.out.println("Please enter a date from 1998 - 2015 (the format should be like this: 2000.12.31.):");
        return LocalDate.parse(scanner.nextLine(), FORMATTER);
    }

    public List<Outcome> readOutcomes() {
        System.out.println("Please enter the 14 outcomes (for example: 11xx22x11x22x1):");
        String s = scanner.nextLine().toUpperCase();
        List<Outcome> outcomes = new ArrayList<>();

        for(char c : s.toCharArray()) {
            switch (c) {
                case '1':
                    outcomes.add(Outcome._1);
                    break;
                case '2':
                    outcomes.add(Outcome._2);
                    break;
                case 'X':
                    outcomes.add(Outcome.X);
            }
        }
        return outcomes;
    }

}
