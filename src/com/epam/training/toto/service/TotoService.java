package com.epam.training.toto.service;
import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TotoService {
    private String filename = "C:\\Projects\\toto-application\\src\\toto.csv";
    private File file = new File(filename);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");


    private List<Round> rounds = new ArrayList<>();



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
        int hitCounter = 0;
        Round round = new Round();

        while (rowScanner.hasNext()) {
            initRounds(rowScanner, formatter, round);
            initHits(rowScanner, hitCounter, round);
            initOutcomes(rowScanner, round);
            rounds.add(round);
        }
    }

    private void initRounds(Scanner rowScanner, DateTimeFormatter formatter, Round round) {
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
                round.setDate(LocalDate.ofYearDay(round.getYear(), calculateDaysOfYear(round.getWeek(), round.getRoundOfWeek())));
        } else {
            round.setDate(LocalDate.parse(date, formatter));
        }
    }

    private void initHits(Scanner rowScanner, int hitCounter, Round round) {
        List<Hit> hits = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String numberOfWagers = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            String prize = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            hitCounter += 0;
            Hit hit = new Hit(hitCounter, Integer.parseInt(numberOfWagers) ,Integer.parseInt(prize));
            hits.add(hit);
        }
        round.setHits(hits);
    }

    private void initOutcomes(Scanner rowScanner, Round round) {
        List<Outcome> outcomes = new ArrayList<>();
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
        round.setOutcomes(outcomes);
    }

    public void getLargestPrize() {
        int max = 0;
        for (Round round : rounds) {
            for (Hit hit : round.getHits()) {
                int value = hit.getPrize();
                if (value > max) max = value;
            }
        }
        System.out.println(NumberFormat.getCurrencyInstance(new Locale("hu", "HU")).format(max));
    }


    public void countStatistics(){
        double team1Won = 0;
        double team2Won = 0;
        double draw = 0;
        double all = 0;
        DecimalFormat df = new DecimalFormat("#.##%");

        for (Round round : rounds) {
            for (Outcome outcome : round.getOutcomes()) {
                if (outcome.equals(Outcome._1)) team1Won++;
                if (outcome.equals(Outcome._2)) team2Won++;
                if (outcome.equals(Outcome.X)) draw++;
            }
        }

        all = team1Won + team2Won + draw;

        System.out.println("Statistics:");
        System.out.println("Team 1 won: " + df.format(team1Won / all));
        System.out.println("Team 2 won: " + df.format(team2Won / all));
        System.out.println("Draws: " + df.format(draw / all));
    }

    public void calculateHitsForDate() {
        Scanner scanner = new Scanner(System.in);
        int hitCount = 0;
        int amount = 0;

        System.out.println("Please enter a date:");
        LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.println("Please enter the outcomes:");
        String enteredOutcomes = scanner.nextLine();
        scanner.close();

        for (Round round : rounds) {
            if (round.getDate().equals(date)) {
                for (int i = 0; i < 14; i++) {
                    String outcome = Character.toString(enteredOutcomes.charAt(i));
                    if (round.getOutcomes().get(i).toString().contains(outcome)) {
                        hitCount++;
                    }
                }
                amount = round.getHits().get(14-hitCount).getPrize();
                break;
            }
        }
        System.out.println("Result: hits: " + hitCount + ", amount: " + amount + " Ft");
    }

    private int calculateDaysOfYear(int weeks, int roundOfWeek) {
        return ((weeks * 7) - 6) + roundOfWeek;
    }
}

