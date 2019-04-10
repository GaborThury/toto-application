package com.epam.training.test;

import com.epam.training.toto.domain.*;
import com.epam.training.toto.service.StatisticsCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestStatisticsCalculator {

    private StatisticsCalculator statisticsCalculator = null;

    @Before
    public void init() {
        statisticsCalculator = new StatisticsCalculator();
    }

    @Test
    public void testCalculateLargestPrize() {
        generateTestForCalculateLargestPrize(0, 0);
        generateTestForCalculateLargestPrize(104, 123);
        generateTestForCalculateLargestPrize(421, 141111);
    }

    @Test
    public void testCalculateStatisticsAboutAllOutcomes() {
        generateTestForCalculateStatisticsAboutAllOutcomes(1000, 500, 250, 250);
        generateTestForCalculateStatisticsAboutAllOutcomes(1, 1, 1, 1);
        generateTestForCalculateStatisticsAboutAllOutcomes(0, 0, 0, 0);
    }

    @Test
    public void testCountPercentage() {
        assertEquals("100%", statisticsCalculator.countPercentage(1, 1));
        assertEquals("3,37%", statisticsCalculator.countPercentage(198789, 5896213));
        assertEquals("0%", statisticsCalculator.countPercentage(0, 5));
    }

    @Test
    public void testCountOccurrenceOfOutcomes() {
        List<Outcome> outcomes = generateOutcomes(10,1,3);
        List<Round> rounds = generateRounds(5, null, outcomes);
        assertEquals(50, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome._1));
        assertEquals(5, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome._2));
        assertEquals(15, statisticsCalculator.countOccurrenceOfOutcomes(rounds, Outcome.X));
    }

    @After
    public void destroy() {
        statisticsCalculator = null;
    }


    //region Generators for tests
    public void generateTestForCalculateLargestPrize(int sizeOfHits, int prizeMultiplier) {
        List<Round> rounds = generateRounds(5, generateHits(sizeOfHits, prizeMultiplier), null);
        Prize prize = new Prize(sizeOfHits * prizeMultiplier, null);
        assertEquals(prize.getAmount(), statisticsCalculator.calculateLargestPrize(rounds).getAmount());
    }

    private void generateTestForCalculateStatisticsAboutAllOutcomes(int numberOfRounds, int team1won, int team2won, int draw) {
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

    private List<Round> generateRounds(int numberOfRounds, List<Hit> hits, List<Outcome> outcomes) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(new Round(0, 0, 0, null, hits, outcomes));
        }
        return rounds;
    }

    private List<Hit> generateHits(int sizeOfHits, int prizeMultiplier) {
        List<Hit> hits = new ArrayList<>();
        for (int i = 1; i <= sizeOfHits; i++) {
            Hit hit = new Hit(0, 0, i * prizeMultiplier);
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
