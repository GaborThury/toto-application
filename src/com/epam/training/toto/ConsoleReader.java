package com.epam.training.toto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");
    private static Scanner scanner = new Scanner(System.in);


    public LocalDate readDate () throws DateTimeParseException {
        System.out.println("Please enter a date from 1998 - 2015 (the format should be like this: 2000.11.28.):");
        return LocalDate.parse(scanner.nextLine(), FORMATTER);
    }

    public String readOutcomes() {
        System.out.println("Please enter the 14 outcomes (for example: 11xx22x11x22x1):");
        return scanner.nextLine();
    }

}
