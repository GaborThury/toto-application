package com.epam.training.toto.service;

import com.epam.training.toto.domain.CsvRow;
import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;
import com.sun.rowset.internal.Row;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParserForToto {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");

    public List<Round> parseCsvToRounds(List<CsvRow> lists){
        List<Round> rounds = new ArrayList<>();
        lists.forEach((c) -> {
            Round round = new Round();
            try {
                round.setYear(Integer.parseInt(c.getColumnObject(1).toString()));
                round.setWeek(Integer.parseInt(c.getColumnObject(2).toString()));
                round.setRoundOfWeek(parseRoundOfWeek(c.getColumnObject(3).toString()));
                round.setDate(
                        parseDate(
                                c.getColumnObject(4).toString(),
                                round.getYear(),
                                round.getWeek(),
                                round.getRoundOfWeek()
                        ));
                round.setHits(parseHits(c.getRowElements(5, 15)));
                round.setOutcomes(parseOutcomes(c.getRowElements(15, 29)));
                rounds.add(round);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return rounds;
    }

    private LocalDate parseDate(String date, int year, int week, int roundOfWeek) {
        if (date.isEmpty()) {
            return LocalDate.ofYearDay(year, calculateDaysOfYear(week, roundOfWeek));
        }
        return LocalDate.parse(date, FORMATTER);
    }


    private int parseRoundOfWeek(String s) {
        if (("-").equals(s)) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    private List<Hit> parseHits(List<Object> list) {
        List<Hit> hits = new ArrayList<>();
        int hitCounter = 14;

        for (int i = 0; i < list.size(); i++) {
            int numberOfWagers = parseHitAttribute(list.get(i).toString());
            i++;
            int prize = parseHitAttribute(list.get(i).toString());
            Hit hit = new Hit(hitCounter, numberOfWagers, prize);
            hits.add(hit);
            hitCounter--;
        }
        return hits;
    }

    private int parseHitAttribute(String s) {
        return Integer.parseInt(s.replaceAll("\\s", "").replaceAll("Ft", ""));
    }

    private List<Outcome> parseOutcomes(List<Object> list) {
        List<Outcome> outcomes;

        outcomes = list.stream().map(s -> {
            String outcome = s.toString().replaceAll("\\+", "").toUpperCase();
            switch (outcome) {
                case "1":
                    return Outcome._1;
                case "2":
                    return Outcome._2;
                case "X":
                    return Outcome.X;
                default:
                    return null;
            }
        }).collect(Collectors.toList());
        return outcomes;
    }

    private int calculateDaysOfYear(int weeks, int roundOfWeek) {
        return ((weeks * 7) - 6) + roundOfWeek;
    }
}