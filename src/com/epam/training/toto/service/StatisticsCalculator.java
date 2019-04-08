package com.epam.training.toto.service;

import com.epam.training.toto.domain.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

public class StatisticsCalculator {
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##%");

    public int calculateLargestPrice(List<Round> rounds) {
        return rounds.stream()
                .flatMap(r -> r.getHits().stream())
                .map(Hit::getPrize)
                .max(Integer::compareTo)
                .get();
    }

    public WinCount calculateStatisticsAboutAllOutcomes(List<Round> rounds) {
        WinCount winCount = new WinCount();
        int team1Won = countOccurrenceOfOutcomes(rounds, Outcome._1);
        int team2Won = countOccurrenceOfOutcomes(rounds, Outcome._2);
        int draw = countOccurrenceOfOutcomes(rounds, Outcome.X);

        winCount.setNumberOfTeam1Wins(team1Won);
        winCount.setNumberOfTeam2Wins(team2Won);
        winCount.setNumberOfDraws(draw);
        return winCount;
    }



    public String countPercentage(double outcome, double all) {
        return DECIMALFORMAT.format(outcome / all);
    }

    public BetResult calculateBetResults(List<Round> rounds, LocalDate date, List<Outcome> userBet) {
        int hitCount = 0;
        int amount = 0;
        BetResult betResult = new BetResult();

        Round round = rounds
                .stream()
                .filter(f -> !(f.getDate().isBefore(date)))
                .min(Comparator.comparing(Round::getDate))
                .orElse(null);

        if (round == null) {
            return null;
        }

        // Counts how many hits does the user have for the round's outcome
        hitCount = IntStream.range(0, userBet.size())
                .mapToObj(i -> compareOutcomes(round.getOutcomes().get(i), userBet.get(i)))
                .reduce(Integer::sum)
                .get();


        if (hitCount >= 10) {
            int finalHitCount = hitCount;
            amount = Objects.requireNonNull(round.getHits().stream()
                    .filter(f -> f.getHitCount() == finalHitCount)
                    .findFirst()
                    .orElse(null)).getPrize();
        }

        betResult.setHitcount(hitCount);
        betResult.setAmount(amount);
        return betResult;
    }

    private int compareOutcomes(Outcome o1, Outcome o2) {
        return o1 == o2 ? 1 : 0;
    }

    private int countOccurrenceOfOutcomes(List<Round> rounds, Outcome o) {
        return (int) rounds.stream()
                .flatMap(r -> r.getOutcomes().stream())
                .filter(o::equals)
                .count();
    }
}
