package com.epam.training.toto.service;

import com.epam.training.toto.domain.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class StatisticsCalculator {
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##%");

    public int calculateLargestPrice(List<Round> rounds) {
        int max = 0;

        for (Round round : rounds) {
            for (Hit hit : round.getHits()) {
                int value = hit.getPrize();
                if (value > max) max = value;
            }
        }
        return max;
    }

    public WinCount calculateStatisticsAboutAllOutcomes(List<Round> rounds) {
        WinCount winCount = new WinCount();
        int team1Won = 0;
        int team2Won = 0;
        int draw = 0;

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
        winCount.setNumberOfTeam1Wins(team1Won);
        winCount.setNumberOfTeam2Wins(team2Won);
        winCount.setNumberOfDraws(draw);
        return winCount;
    }


    public String summarizeStatistics(WinCount winCount) {
        double team1Won = winCount.getNumberOfTeam1Wins();
        double team2Won = winCount.getNumberOfTeam2Wins();
        double draw = winCount.getNumberOfDraws();
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

    public BetResult calculateBetResults(List<Round> rounds, LocalDate date, List<Outcome> userBet) {
        int hitCount = 0;
        int amount = 0;
        BetResult betResult = new BetResult();

        Round round = rounds
                .stream()
                .filter(f -> f.getDate().isAfter(date) || f.getDate().isEqual(date))
                .sorted()
                .findFirst()
                .orElse(null);

        if (round == null) {
            return null;
        }

        for (int i = 0; i < userBet.size(); i++) {
            if (round.getOutcomes().get(i) == (userBet.get(i))) {
                hitCount++;
            }
        }

        if (hitCount >= 10) {
            int finalHitCount = hitCount;
            amount = Objects.requireNonNull(round.getHits().stream()
                    .filter(f -> f.getHitCount() == finalHitCount)
                    .findAny()
                    .orElse(null)).getPrize();
        }

        betResult.setHitcount(hitCount);
        betResult.setAmount(amount);
        return betResult;
    }
}
