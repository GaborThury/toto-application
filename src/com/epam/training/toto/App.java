package com.epam.training.toto;

import com.epam.training.toto.domain.CsvRow;
import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.*;
import com.sun.rowset.internal.Row;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        CsvParserForToto csvParserForToto = new CsvParserForToto();
        TotoService totoService = new TotoService();
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
        UserInputReader userInputReader = new UserInputReader();
        Validator validator = new Validator();

        // Give the input file
        List<CsvRow> list = csvReader.readFile("toto.csv");

        // Parse the input file to rounds
        List<Round> rounds = csvParserForToto.parseCsvToRounds(list);

        //Print the largest price ever recorded
        totoService.printLargestPrice(rounds, statisticsCalculator);

        //Print statistics about the outcomes (1 X 2)
        totoService.printStatisticsAboutAllOutcomes(rounds, statisticsCalculator);

        // Read the user input from console and print the results
        totoService.printUserGivenBetResults(rounds, statisticsCalculator, userInputReader, validator);

    }
}
