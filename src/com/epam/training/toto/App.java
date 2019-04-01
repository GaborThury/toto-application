package com.epam.training.toto;

import com.epam.training.toto.service.TotoService;

public class App {
    public static void main(String[] args) {
        //TODO: Entry point for the application. It uses TotoService to get results and prints them to the console
        TotoService totoService = new TotoService();
        totoService.init();
        System.out.println("The largest prize ever recorded: " + totoService.getLargestPrize());

        totoService.countStatistics();

    }
}
