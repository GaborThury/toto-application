package com.epam.training.toto.service;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TotoService {
    private String filename = "C:\\Projects\\toto-application\\src\\toto.csv";
    private File file = new File(filename);
    // private List<List<String>> records = new ArrayList<>();

    private List<Round> rounds = new ArrayList<>();
    private List<Hit> hits = new ArrayList<>();
    private List<Outcome> outcomes = new ArrayList<>();

    public void init() {
        {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    getRecordFromLine(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void getRecordFromLine(String line) {
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");
        int hitCounter = 0;

        while (rowScanner.hasNext()) {
            initRounds(rowScanner, formatter);
            initHits(rowScanner, hitCounter);
            initOutcomes(rowScanner);
        }
    }

    private void initRounds(Scanner rowScanner, DateTimeFormatter formatter) {
        Round round = new Round();
        round.setYear(Integer.parseInt(rowScanner.next()));
        round.setWeek(Integer.parseInt(rowScanner.next()));
        String roundOfWeek = rowScanner.next();
        if (roundOfWeek.equals("-")) {
            round.setRoundOfWeek(0);
        } else {
            round.setRoundOfWeek(Integer.parseInt(roundOfWeek));
        }
        String date = rowScanner.next();
        if (date.equals("")) {
            round.setDate(null);
        } else {
            round.setDate(LocalDate.parse(date, formatter));
        }

        rounds.add(round);
    }

    private void initHits(Scanner rowScanner, int hitCounter) {
        for (int i = 0; i < 5; i++) {
            String numberOfWagers = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            String prize = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            hitCounter += 0;
            Hit hit = new Hit(hitCounter, Integer.parseInt(numberOfWagers) ,Integer.parseInt(prize));
            hits.add(hit);
        }
    }

    private void initOutcomes(Scanner rowScanner) {
        for (int i = 0; i < 14; i++) {
            String outcome = rowScanner.next().replaceAll("\\+", "").toUpperCase();
            switch (outcome) {
                case "1":
                    outcomes.add(Outcome._1);
                    break;
                case "2":
                    outcomes.add(Outcome._2);
                    break;
                case "X":
                    outcomes.add(Outcome.X);
            }
        }
    }

    public int getLargestPrize() {
        int max = 0;
        for (Hit hit : hits) {
            int value = hit.getPrize();
            if (value > max) max = value;
        }
        return max;
    }

    public void countStatistics(){
        double team1Won = 0;
        double team2Won = 0;
        double draw = 0;
        double all = 0;
        DecimalFormat df = new DecimalFormat("#.##%");

        for (Outcome o : outcomes) {
            if (o.equals(Outcome._1)) team1Won++;
            if (o.equals(Outcome._2)) team2Won++;
            if (o.equals(Outcome.X)) draw++;
        }
        all = team1Won + team2Won + draw;
        
        System.out.println("Team 1 won: " + df.format(team1Won / all));
        System.out.println("Team 2 won: " + df.format(team2Won / all));
        System.out.println("Draws: " + df.format(draw / all));

    }

}
