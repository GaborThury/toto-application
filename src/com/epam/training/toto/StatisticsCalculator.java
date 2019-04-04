package com.epam.training.toto;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.System.out;

public class StatisticsCalculator {
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##%");

    public String calculateLargestPrice(List<Round> rounds) {
        int max = 0;
        for (Round round : rounds) {
            for (Hit hit : round.getHits()) {
                int value = hit.getPrize();
                if (value > max) max = value;
            }
        }
        return NumberFormat.getCurrencyInstance(new Locale("hu", "HU")).format(max);
    }

    public String calculateStatistics(List<Round> rounds) {
        double team1Won = 0;
        double team2Won = 0;
        double draw = 0;

        for (Round round : rounds) {
            for (Outcome outcome : round.getOutcomes()) {
                if (Outcome._1.equals(outcome)){
                    team1Won++;
                } else if (Outcome._2.equals(outcome)) {
                    team2Won++;
                } else if (Outcome.X.equals(outcome)) {
                    draw++;
                }
            }
        }
        return summarizeStatistics(team1Won, team2Won, draw);
    }

    private String summarizeStatistics(double team1Won, double team2Won, double draw) {
        double all = team1Won + team2Won + draw;
        StringBuilder sb = new StringBuilder();
        sb.append("Statistics: Team #1 won: ");
        sb.append(countPercentage(team1Won, all));
        sb.append(", Team #2 won: ");
        sb.append(countPercentage(team2Won, all));
        sb.append(", Draws: ");
        sb.append(countPercentage(draw, all));
        return sb.toString();
    }

    private String countPercentage(double outcome, double all) {
        return DECIMALFORMAT.format(outcome / all);
    }

    public void calculateHitsForDate(List<Round> rounds) {
        Validator validator = new Validator();
        LocalDate date = validator.getValidDateFromConsole();
        String enteredOutcomes = validator.getValidOutcomesFromConsole();
        int hitCount = 0;
        int amount = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(localDateToDate(date));

        for (Round round : rounds) {
            if ((round.getYear() == date.getYear()) && (round.getWeek() == calendar.get(Calendar.WEEK_OF_YEAR))) {
                for (int i = 0; i < 14; i++) {
                    String outcome = Character.toString(enteredOutcomes.charAt(i));
                    if (round.getOutcomes().get(i).toString().contains(outcome)) {
                        hitCount++;
                    }
                }
                if (hitCount < 10) {
                    amount = 0;
                } else {
                    amount = round.getHits().get(14 - hitCount).getPrize();
                }
                out.println(round.getHits().toString());
                break;
            }
        }
        System.out.println("Result: hits: " + hitCount + ", amount: " + amount + " Ft");
    }
    private static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
