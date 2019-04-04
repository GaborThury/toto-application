package com.epam.training.toto;

import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import com.epam.training.toto.domain.Round;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.d.");

    public List<Round> parseCsv(List<List<String>> lists) {
        List<Round> rounds = new ArrayList<>();
        lists.forEach((c) -> {
            Round round = new Round();
            round.setYear(Integer.parseInt(c.get(0)));
            round.setWeek(Integer.parseInt(c.get(1)));
            round.setRoundOfWeek(parseRoundOfWeek(c.get(2)));
            round.setDate(parseDate(c.get(3)));
            round.setHits(parseHits(c.subList(4, 14)));
            round.setOutcomes(parseOutcomes(c.subList(14, 28)));
            rounds.add(round);
        });
        return rounds;
    }

    private LocalDate parseDate(String date) {
        // TODO: create date value if it is an empty string
        return date.isEmpty() ? null : LocalDate.parse(date, FORMATTER);
    }

    private int parseRoundOfWeek(String s) {
        if (("-").equals(s)) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    private List<Hit> parseHits(List<String> list) {
        List<Hit> hits = new ArrayList<>();
        int hitCounter = 14;

        for (int i = 0; i < list.size(); i++) {
            int numberOfWagers = parseHitAttribute(list.get(i));
            i++;
            int prize = parseHitAttribute(list.get(i));
            Hit hit = new Hit(hitCounter, numberOfWagers, prize);
            hits.add(hit);
            hitCounter--;
        }
        //list.stream().filter(element -> element.contains("Ft")).collect(Collectors.toList());
        return hits;
    }

    private int parseHitAttribute(String s) {
        return Integer.parseInt(s.replaceAll("\\s", "").replaceAll("Ft", ""));
    }

    private List<Outcome> parseOutcomes(List<String> list) {
        List<Outcome> outcomes = new ArrayList<>();
        for (String s : list) {
            String outcome = s.replaceAll("\\+", "").toUpperCase();
            switch (outcome) {
                case "1":
                    outcomes.add(Outcome._1);
                    break;
                case "2":
                    outcomes.add(Outcome._2);
                    break;
                case "X":
                    outcomes.add(Outcome.X);
            }
        }
        return outcomes;
    }
}
