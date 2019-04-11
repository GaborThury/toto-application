package com.epam.training.test;
import com.epam.training.toto.service.UserGivenInputValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class TestUserGivenInputValidator {

    private UserGivenInputValidator testUserGivenInputValidator = null;

    @Before
    public void init() {
        testUserGivenInputValidator = new UserGivenInputValidator();
    }

    @Test
    public void testValidateDateForRightInput() {
        LocalDate localDate = LocalDate.of(2003, 11, 13);
        assertEquals(localDate, testUserGivenInputValidator.validateDate("2003.11.13."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDateForOutOfValidRange() {
        testUserGivenInputValidator.validateDate("1997.12.31.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateDateForOutOfValidRange2() {
        testUserGivenInputValidator.validateDate("2016.01.01.");
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateDateForInvalidFormat() {
        testUserGivenInputValidator.validateDate("31.12.2000");
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateDateForInvalidDelimeter() {
        testUserGivenInputValidator.validateDate("2004-04-04");
    }

    @Test(expected = DateTimeParseException.class)
    public void testValidateDateForInvalidDelimeter2() {
        testUserGivenInputValidator.validateDate("2004_04_04");
    }

    @Test
    public void testValidateOutcomesForNormalInput() {
        assertTrue(testUserGivenInputValidator.validateOutcomes("xxXX1122x21211"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOutcomesForInvalidInput() {
        testUserGivenInputValidator.validateOutcomes("31111222211211");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOutcomesForNotEnoughCharacters() {
        testUserGivenInputValidator.validateOutcomes("1212xx11");
    }

    @After
    public void destroy(){
        testUserGivenInputValidator = null;
    }

}
