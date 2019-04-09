package com.epam.training.toto.service;

import com.epam.training.toto.domain.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

public class TotoService {

    private StatisticsCalculator statisticsCalculator;
    private UserInputReader userInputReader;

    public TotoService(StatisticsCalculator statisticsCalculator, UserInputReader userInputReader) {
        this.statisticsCalculator = statisticsCalculator;
        this.userInputReader = userInputReader;
    }

    public void printLargestPrice(List<Round> rounds) {
        Prize prize = statisticsCalculator.calculateLargestPrize(rounds);
        out.println("The largest prize ever recorded: " +
                NumberFormat.getIntegerInstance().format(prize.getAmount()) + " " + prize.getCurrency());
    }

    public void printStatisticsAboutAllOutcomes(List<Round> rounds) {
        WinCount winCount = statisticsCalculator.calculateStatisticsAboutAllOutcomes(rounds);
        out.println(summarizeStatistics(winCount));
    }

    public void printUserGivenBetResults(List<Round> rounds) {
        LocalDate localDate = userInputReader.readDate();
        List<Outcome> userBet = userInputReader.readOutcomes();

        BetResult betResult = statisticsCalculator.calculateBetResults(rounds, localDate, userBet);
        System.out.printf("The result of you bet: hits: %d, amount: %d Ft",
                betResult.getHitcount(), betResult.getAmount());
    }

    public String summarizeStatistics(WinCount winCount) {
        double team1Won = winCount.getNumberOfTeam1Wins();
        double team2Won = winCount.getNumberOfTeam2Wins();
        double draw = winCount.getNumberOfDraws();
        double all = team1Won + team2Won + draw;

        StringBuilder sb = new StringBuilder();
        sb.append("Statistics: Team #1 won: ");
        sb.append(statisticsCalculator.countPercentage(team1Won, all));
        sb.append(", Team #2 won: ");
        sb.append(statisticsCalculator.countPercentage(team2Won, all));
        sb.append(", Draws: ");
        sb.append(statisticsCalculator.countPercentage(draw, all));
        return sb.toString();
    }

}
