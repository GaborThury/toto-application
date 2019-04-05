package com.epam.training.toto.service;

import com.epam.training.toto.StatisticsCalculator;
import com.epam.training.toto.UserInputReader;
import com.epam.training.toto.Validator;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Price;
import com.epam.training.toto.domain.Round;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static java.lang.System.out;

public class TotoService {

    public void printLargestPrice(List<Round> rounds, StatisticsCalculator statisticsCalculator) {
        out.println("The largest prize ever recorded: " +
                NumberFormat
                .getCurrencyInstance(new Locale("hu", "HU"))
                .format(statisticsCalculator.calculateLargestPrice(rounds)));
    }

    public void printStatistics(List<Round> rounds, StatisticsCalculator statisticsCalculator) {
        out.println(statisticsCalculator.calculateStatistics(rounds));
    }

    public void calculateHitsForDate(List<Round> rounds, StatisticsCalculator statisticsCalculator, UserInputReader userInputReader, Validator validator) {
        LocalDate localDate;
        List<Outcome> userBet;

        while (true) {
            try {
                localDate = userInputReader.readDate();
                validator.validateDate(localDate);
                userBet = userInputReader.readOutcomes();
                validator.validateOutcomes(userBet);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("The date you have entered is not valid!" +
                        " Please remember to use this format: 2000.01.01.");
            } catch (IllegalArgumentException e) {
                System.out.println("You entered invalid input!");
            }
        }
        statisticsCalculator.calculateHitsForDate(rounds, localDate, userBet);
    }
}
