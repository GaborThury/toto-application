package com.epam.training.toto.service;

import com.epam.training.toto.domain.Outcome;

import java.time.LocalDate;
import java.util.List;

public class Validator {

    public boolean validateDate(LocalDate localDate) throws IllegalArgumentException {
        if ((localDate.getYear() > 1998) && (localDate.getYear() < 2016)) {
            return true;
        } else {
            throw  new IllegalArgumentException();
        }
    }

    public boolean validateOutcomes(List<Outcome> outcomes) {
        if (outcomes.size() == 14) {
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
