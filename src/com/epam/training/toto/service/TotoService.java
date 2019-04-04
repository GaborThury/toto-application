package com.epam.training.toto.service;

import com.epam.training.toto.StatisticsCalculator;
import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class TotoService {
    // can it be a constant?
    private StatisticsCalculator statisticsCalculator = new StatisticsCalculator();

    public String printLargestPrice(List<Round> rounds) {
        return statisticsCalculator.calculateLargestPrice(rounds);
    }

    public String printStatistics(List<Round> rounds) {
        return statisticsCalculator.calculateStatistics(rounds);
    }


    public void calculateHitsForDate(List<Round> rounds) {
        statisticsCalculator.calculateHitsForDate(rounds);
    }

    private int calculateDaysOfYear(int weeks, int roundOfWeek) {
        return ((weeks * 7) - 6) + roundOfWeek;
    }




}
