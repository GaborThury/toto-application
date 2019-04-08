package com.epam.training.toto.service;

import com.epam.training.toto.domain.Outcome;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UserInputReader {
    private static Scanner scanner = new Scanner(System.in);
    private UserGivenInputValidator userGivenInputValidator;

    public UserInputReader(UserGivenInputValidator userGivenInputValidator) {
        this.userGivenInputValidator = userGivenInputValidator;
    }


    public LocalDate readDate() throws DateTimeParseException, IllegalArgumentException {
        while (true) {
            try {
                System.out.println("Please enter a date from 1998 - 2015 (the format should be like this: 2000.12.31.):");
                String input = scanner.nextLine();
                return userGivenInputValidator.validateDate(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("You entered invalid date. Please remember to use correct format.");
            }
        }
    }

    public List<Outcome> readOutcomes() {
        while (true) {
            try {
                System.out.println("Please enter the 14 outcomes (for example: 11xx22x11x22x1):");
                String input = scanner.nextLine().toUpperCase();
                userGivenInputValidator.validateOutcomes(input); //If it is not valid, validator throws an exception
                char[] inputCharArray = input.toCharArray();

                return IntStream.range(0, inputCharArray.length)
                        .mapToObj(i -> charToOutcome(inputCharArray[i]))
                        .collect(Collectors.toList());

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Outcome charToOutcome(char c) {
        switch (c) {
            case '1':
                return Outcome._1;
            case '2':
                return Outcome._2;
            case 'X':
                return Outcome.X;
            default:
                return null;
        }
    }
}
