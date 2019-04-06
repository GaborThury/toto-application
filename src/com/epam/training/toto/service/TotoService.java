package com.epam.training.toto.service;

import com.epam.training.toto.domain.BetResult;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.domain.WinCount;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public void printStatisticsAboutAllOutcomes(List<Round> rounds, StatisticsCalculator statisticsCalculator) {
        WinCount winCount = statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds);
        out.println(statisticsCalculator.summarizeStatistics(winCount));
    }

    public void printUserGivenBetResults(List<Round> rounds, StatisticsCalculator statisticsCalculator, UserInputReader userInputReader, Validator validator) {
        LocalDate localDate;
        List<Outcome> userBet;
        BetResult betResult;

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
        betResult = statisticsCalculator.calculateBetResults(rounds, localDate, userBet);

        System.out.printf("The result of you bet: hits: %d, amount: %d Ft", betResult.getHitcount(), betResult.getAmount());


    }
}
