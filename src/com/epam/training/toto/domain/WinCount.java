package com.epam.training.toto.domain;

public class WinCount {
    private int numberOfTeam1Wins;
    private int numberOfTeam2Wins;
    private int numberOfDraws;

    public int getNumberOfTeam1Wins() {
        return numberOfTeam1Wins;
    }

    public void setNumberOfTeam1Wins(int numberOfTeam1Wins) {
        this.numberOfTeam1Wins = numberOfTeam1Wins;
    }

    public int getNumberOfTeam2Wins() {
        return numberOfTeam2Wins;
    }

    public void setNumberOfTeam2Wins(int numberOfTeam2Wins) {
        this.numberOfTeam2Wins = numberOfTeam2Wins;
    }

    public int getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(int numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }
}
