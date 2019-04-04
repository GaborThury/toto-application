package com.epam.training.toto;

import com.epam.training.toto.domain.Round;
import com.epam.training.toto.service.TotoService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        CsvParser csvParser = new CsvParser();
        TotoService totoService = new TotoService();

        List<List<String>> list = csvReader.readFile("toto.csv");
        List<Round> rounds = csvParser.parseCsv(list);

        System.out.println("The largest prize ever won: " + totoService.printLargestPrice(rounds));
        System.out.println(totoService.printStatistics(rounds));
        totoService.calculateHitsForDate(rounds);

    }
}
