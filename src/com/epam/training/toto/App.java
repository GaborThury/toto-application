package com.epam.training.toto;

import com.epam.training.toto.service.TotoService;

public class App {
    public static void main(String[] args) {
        TotoService totoService = new TotoService();
        totoService.init();
        totoService.getLargestPrize();
        totoService.countStatistics();

        totoService.calculateHitsForDate();

    }
}
