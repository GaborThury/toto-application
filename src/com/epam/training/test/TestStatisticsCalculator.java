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
        generateTestForCalculateLargestPrize(3, 5);
        generateTestForCalculateLargestPrize(0, 0);
        generateTestForCalculateLargestPrize(7, 1);
        generateTestForCalculateLargestPrize(104, 123);
        generateTestForCalculateLargestPrize(421, 141111);
    }

    @Test
    public void testCalculateStatisticsAboutAllOutcomes() {
        generateTestForCalculateStatisticsAboutAllOutcomes(3, 10, 12, 3);
        generateTestForCalculateStatisticsAboutAllOutcomes(5, 1000, 42, 24);
        generateTestForCalculateStatisticsAboutAllOutcomes(100, 102, 0, 64);
        generateTestForCalculateStatisticsAboutAllOutcomes(1024, 42, 1, 3);
        generateTestForCalculateStatisticsAboutAllOutcomes(4, 53, 200, 0);
    }

    @Test
    public void testCountPercentage() {
        assertEquals("100%", statisticsCalculator.countPercentage(1, 1));
        assertEquals("50%", statisticsCalculator.countPercentage(1, 2));
        assertEquals("60%", statisticsCalculator.countPercentage(3, 5));
        assertEquals("20,78%", statisticsCalculator.countPercentage(1059, 5097));
        assertNotEquals("20,77%",statisticsCalculator.countPercentage(1059, 5097));
        assertEquals("3,37%", statisticsCalculator.countPercentage(198789, 5896213));
        assertEquals("0%", statisticsCalculator.countPercentage(0, 5));
    }

    @After
    public void destroy() {
        statisticsCalculator = null;
    }

    public void generateTestForCalculateLargestPrize(int sizeOfHits, int prizeMultiplier) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rounds.add(new Round(0, 0, 0, null, generateHits(sizeOfHits, prizeMultiplier), null));
        }
        Prize prize = new Prize(sizeOfHits * prizeMultiplier, null);
        assertEquals(prize.getAmount(), statisticsCalculator.calculateLargestPrize(rounds).getAmount());
    }

    private List<Hit> generateHits(int sizeOfHits, int prizeMultiplier) {
        List<Hit> hits = new ArrayList<>();
        for (int i = 1; i <= sizeOfHits; i++) {
            Hit hit = new Hit(0, 0, i * prizeMultiplier);
            hits.add(hit);
        }
        return hits;
    }

    private void generateTestForCalculateStatisticsAboutAllOutcomes(int numberOfRounds, int team1won, int team2won, int draw) {
        List<Round> rounds = new ArrayList<>();
        List<Outcome> outcomes = generateOutcomes(team1won, team2won, draw);

        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(new Round(0, 0, 0, null, null, outcomes));
        }

        WinCount winCount = new WinCount();
        winCount.setNumberOfTeam1Wins(team1won * numberOfRounds);
        winCount.setNumberOfTeam2Wins(team2won * numberOfRounds);
        winCount.setNumberOfDraws(draw * numberOfRounds);

        assertEquals(winCount.getNumberOfTeam1Wins(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfTeam1Wins());
        assertEquals(winCount.getNumberOfTeam2Wins(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfTeam2Wins());
        assertEquals(winCount.getNumberOfDraws(), statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds).getNumberOfDraws());
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
}
