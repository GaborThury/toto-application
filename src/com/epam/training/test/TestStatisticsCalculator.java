package com.epam.training.test;

import com.epam.training.toto.domain.*;
import com.epam.training.toto.service.StatisticsCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestStatisticsCalculator {

    private StatisticsCalculator statisticsCalculator = null;

    @Before
    public void init() {
        statisticsCalculator = new StatisticsCalculator();
    }

    @Test
    public void testCalculateLargestPrize() {
        int numberOfRounds = 5;
        int sizeOfHits = 7;
        List<Round> rounds = generateRounds(numberOfRounds, generateHits(sizeOfHits), null);
        Prize prize = new Prize(sizeOfHits, null);

        assertEquals(prize.getAmount(), statisticsCalculator.calculateLargestPrize(rounds).getAmount());
    }

    @Test
    public void testCalculateLargestPrizeForNullInput() {
        assertNull(statisticsCalculator.calculateLargestPrize(null));
    }

    @Test
    public void testCalculateStatisticsAboutAllOutcomes() {
        int team1won = 13;
        int team2won = 7;
        int draw = 5;
        int numberOfRounds = 17;

        List<Outcome> outcomes = generateOutcomes(team1won, team2won, draw);
        List<Round> rounds = generateRounds(numberOfRounds, null, outcomes);

        WinCount winCount = new WinCount();
        winCount.setNumberOfTeam1Wins(team1won * numberOfRounds);
        winCount.setNumberOfTeam2Wins(team2won * numberOfRounds);
        winCount.setNumberOfDraws(draw * numberOfRounds);

        assertEquals(winCount.getNumberOfTeam1Wins(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfTeam1Wins());
        assertEquals(winCount.getNumberOfTeam2Wins(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfTeam2Wins());
        assertEquals(winCount.getNumberOfDraws(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfDraws());
    }

    @Test
    public void testCountPercentage() {
        assertEquals("100%", statisticsCalculator.countPercentage(1, 1));
        assertEquals("3,37%", statisticsCalculator.countPercentage(198789, 5896213));
        assertEquals("0%", statisticsCalculator.countPercentage(0, 5));
    }

    @Test
    public void testCountOccurrenceOfOutcomes() {
        int team1won = 13;
        int team2won = 7;
        int draw = 5;
        int numberOfRounds = 17;
        List<Outcome> outcomes = generateOutcomes(team1won,team2won,draw);
        List<Round> rounds = generateRounds(numberOfRounds, null, outcomes);
        assertEquals(numberOfRounds * team1won, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome._1));
        assertEquals(numberOfRounds * team2won, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome._2));
        assertEquals(numberOfRounds * draw, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome.X));
    }

    @Test
    public void testCalculateBetResults() {
        LocalDate userDate = LocalDate.of(2013, 3, 22);

        // Need to create 3 localdate values, 1 is matching to user input
        LocalDate roundInputDate1 = LocalDate.of(2013, 3, 21);
        LocalDate roundInputDate2 = LocalDate.of(2013, 3, 22);
        LocalDate roundInputDate3 = LocalDate.of(2013, 3, 23);

        List<Outcome> userBet = generateOutcomes(10,3,1);

        // Create 3 rounds with the 3 different dates
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round(0, 0, 0, roundInputDate1, generateHits(5), generateOutcomes(10, 1, 3)));
        rounds.add(new Round(0, 0, 0, roundInputDate2, generateHits(5), generateOutcomes(10, 2, 2)));
        rounds.add(new Round(0, 0, 0, roundInputDate3, generateHits(5), generateOutcomes(5, 4, 5)));

        // According to the given values above, this should be the result
        BetResult betResult = new BetResult();
        betResult.setAmount(2);
        betResult.setHitcount(13);

        assertEquals(betResult.getAmount(), statisticsCalculator.calculateBetResults(rounds, userDate, userBet).getAmount());
        assertEquals(betResult.getHitcount(), statisticsCalculator.calculateBetResults(rounds, userDate, userBet).getHitcount());
    }

    @Test
    public void testCompareOutcomes() {
        assertEquals(1, statisticsCalculator.compareOutcomes(Outcome._1, Outcome._1));
        assertEquals(0, statisticsCalculator.compareOutcomes(Outcome._2, Outcome.X));
    }

    @After
    public void destroy() {
        statisticsCalculator = null;
    }


    //region Generators for tests

    private List<Round> generateRounds(int numberOfRounds, List<Hit> hits, List<Outcome> outcomes) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(new Round(0, 0, 0, null, hits, outcomes));
        }
        return rounds;
    }

    private List<Hit> generateHits(int sizeOfHits) {
        List<Hit> hits = new ArrayList<>();
        for (int i = 1; i <= sizeOfHits; i++) {
            Hit hit = new Hit(15 - i, 0, i);
            hits.add(hit);
        }
        return hits;
    }

    private List<Outcome> generateOutcomes(int team1Won, int team2Won, int draw) {
        List<Outcome> outcomes = new ArrayList<>();
        for (int i = 0; i < team1Won; i++) {
            outcomes.add(Outcome._1);
        }
        for (int i = 0; i < team2Won; i++) {
            outcomes.add(Outcome._2);
        }
        for (int i = 0; i < draw; i++) {
            outcomes.add(Outcome.X);
        }
        return outcomes;
    }

    //endregion

}
