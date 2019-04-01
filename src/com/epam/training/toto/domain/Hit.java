package com.epam.training.toto.domain;

public class Hit {
    private int hitCount;
    private int numberOfWagers;
    private int prize;

    public Hit(int hitCount, int numberOfWagers, int prize) {
        this.hitCount = hitCount;
        this.numberOfWagers = numberOfWagers;
        this.prize = prize;
    }


    public int getHitCount() {
        return hitCount;
    }

    public int getNumberOfWagers() {
        return numberOfWagers;
    }

    public int getPrize() {
        return prize;
    }
}