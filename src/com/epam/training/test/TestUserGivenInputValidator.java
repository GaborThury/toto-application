package com.epam.training.test;
import com.epam.training.toto.service.UserGivenInputValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class TestUserGivenInputValidator {

    private UserGivenInputValidator testUserGivenInputValidator = null;

    @Before
    public void init() {
        testUserGivenInputValidator = new UserGivenInputValidator();
    }


    @Test(expected = IllegalArgumentException.class)
    public void validateDate1() {
        testUserGivenInputValidator.validateDate("1000.01.01.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateDate2() {
        testUserGivenInputValidator.validateDate("2016.01.01.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateDate3() {
        testUserGivenInputValidator.validateDate("1000.01.01.");
    }

    @Test(expected = DateTimeParseException.class)
    public void validateDate4() {
        testUserGivenInputValidator.validateDate("31.12.2000");
    }

    @Test(expected = DateTimeParseException.class)
    public void validateDate5() {
        testUserGivenInputValidator.validateDate("2004-04-04");
    }

    @Test(expected = DateTimeParseException.class)
    public void validateDate6() {
        testUserGivenInputValidator.validateDate("2004_04_04");
    }

    @Test
    public void testValidateOutcomes() {
        assertTrue(testUserGivenInputValidator.validateOutcomes("11111111111111"));
        assertTrue(testUserGivenInputValidator.validateOutcomes("xxxxxxXXxxxxxx"));
        assertTrue(testUserGivenInputValidator.validateOutcomes("22222222222222"));
        assertTrue(testUserGivenInputValidator.validateOutcomes("12x12x12x12x11"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOutcomes2() {
        testUserGivenInputValidator.validateOutcomes("31111222211211");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOutcomes3() {
        testUserGivenInputValidator.validateOutcomes("1212xx11");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateOutcomes4() {
        testUserGivenInputValidator.validateOutcomes("02121212121212");
    }


    @After
    public void destroy(){
        testUserGivenInputValidator = null;
    }

}
