package com.epam.training.toto.service;
import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TotoService {
    private String filename = "C:\\Projects\\toto-application\\src\\toto.csv";
    private File file = new File(filename);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.d.");
    private List<Round> rounds = new ArrayList<>();

    // Getting the data from the CSV file
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

    // Getting a single line of record from the CSV file
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

        // Some lines have this value, so it needs to be handled
        if (roundOfWeek.equals("-")) {
            round.setRoundOfWeek(0);
        } else {
            round.setRoundOfWeek(Integer.parseInt(roundOfWeek));
        }
        String date = rowScanner.next();

        // Some lines does not have date value, so it needs to be created according to the specification
        if (date.equals("")) {
                round.setDate(LocalDate.ofYearDay(round.getYear(),
                        calculateDaysOfYear(round.getWeek(),
                        round.getRoundOfWeek())));
        } else {
            round.setDate(LocalDate.parse(date, formatter));
        }
    }

    private void initHits(Scanner rowScanner, int hitCounter, Round round) {
        List<Hit> hits = new ArrayList<>();

        // There can be 5 kind of hits for every round (10, 11, 12, 13 or 14 hits)
        for (int i = 0; i < 5; i++) {
            String numberOfWagers = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            String prize = rowScanner.next().replaceAll("\\s","").replaceAll("Ft", "");
            Hit hit = new Hit(hitCounter, Integer.parseInt(numberOfWagers) ,Integer.parseInt(prize));
            hits.add(hit);
        }
        round.setHits(hits);
    }

    private void initOutcomes(Scanner rowScanner, Round round) {
        List<Outcome> outcomes = new ArrayList<>();

        // There are 14 outcomes
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
            int value = round.getHits().get(0).getPrize(); // Getting the index 0, because it is always the biggest
            if (value > max) max = value;
        }
        System.out.println("The largest prize ever recorded: " +
                NumberFormat.getCurrencyInstance(new Locale("hu", "HU")).format(max));
    }

    public void countStatistics(){
        double team1Won = 0;
        double team2Won = 0;
        double draw = 0;
        double all;
        DecimalFormat df = new DecimalFormat("#.##%");

        for (Round round : rounds) {
            for (Outcome outcome : round.getOutcomes()) {
                if (outcome.equals(Outcome._1)) {
                    team1Won++;
                } else if (outcome.equals(Outcome._2)) {
                    team2Won++;
                } else if (outcome.equals(Outcome.X)) {
                    draw++;
                }
            }
        }

        all = team1Won + team2Won + draw;

        System.out.println("Statistics: Team #1 won: " + df.format(team1Won / all) +
                ", Team #2 won: " + df.format(team2Won / all) +
                ", Draws: " + df.format(draw / all));
    }

    public void calculateHitsForDate() {
        Scanner scanner = new Scanner(System.in);
        String enteredOutcomes;
        LocalDate date;
        int hitCount = 0;
        int amount = 0;

        date = getValidDateFromConsole(scanner);
        enteredOutcomes = getValidOutcomesFromConsole(scanner);
        scanner.close();

        for (Round round : rounds) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(localDateToDate(date));

            if (round.getYear() == date.getYear() && round.getWeek() == calendar.get(Calendar.WEEK_OF_YEAR)) {
                for (int i = 0; i < 14; i++) {
                    String outcome = Character.toString(enteredOutcomes.charAt(i));
                    if (round.getOutcomes().get(i).toString().contains(outcome)) {
                        hitCount++;
                    }
                }
                if (hitCount < 10) {
                    amount = 0;
                } else {
                    amount = round.getHits().get(14-hitCount).getPrize();
                }
                break;
            }
        }
        System.out.println("Result: hits: " + hitCount + ", amount: " + amount + " Ft");
    }

    private LocalDate getValidDateFromConsole(Scanner scanner) {
        LocalDate date = null;
        boolean invalidDate = true;

        while (invalidDate) {
            System.out.println("Please enter a date:");
            try {
                date = LocalDate.parse(scanner.nextLine(), formatter);
                invalidDate = false;
                if (date.getYear() < 1998 || date.getYear() > 2015) {
                    System.out.println("Sorry, we do not have records between 1998 and 2015.");
                    invalidDate = true;
                }
            } catch (Exception e) {
                System.out.println("The date you have entered is not valid!");
            }
        }
        return date;
    }

    private String getValidOutcomesFromConsole(Scanner scanner) {
        String enteredOutcomes = "";
        boolean invalidOutcomes = true;

        while (invalidOutcomes) {
            System.out.println("Please enter the outcomes:");
            enteredOutcomes = scanner.nextLine();
            if (enteredOutcomes.length() != 14 || areInvalidCharacters(enteredOutcomes)) {
                System.out.println("The outcomes you have entered are not valid!");
            } else {
                invalidOutcomes = false;
            }
        }
        return enteredOutcomes;
    }

    private boolean areInvalidCharacters(String enteredOutcomes) {
        for (int i = 0; i < enteredOutcomes.length(); i++) {
            char c = enteredOutcomes.toUpperCase().charAt(i);
            if (c != '1' && c != '2' && c != 'X') {
                return true;
            }
        }
        return false;
    }

    private int calculateDaysOfYear(int weeks, int roundOfWeek) {
        return ((weeks * 7) - 6) + roundOfWeek;
    }

    private static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
