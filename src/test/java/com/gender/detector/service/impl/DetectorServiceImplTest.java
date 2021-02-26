package com.gender.detector.service.impl;

import com.gender.detector.exceptions.GuessMethodNotExistsException;
import com.gender.detector.exceptions.ParameterValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DetectorServiceImplTest {

    DetectorServiceImpl detectorService = new DetectorServiceImpl();

    //    FIRST METHOD TESTS
    @Test
    void shouldReturnMaleUsingFirstMethod() {
        assertEquals("MALE", detectorService.tokenCheck("Jan Maria Rokita", 1));
    }

    @Test
    void shouldReturnFemaleUsingFirstMethod() {
        assertEquals("FEMALE", detectorService.tokenCheck("Maria Jan Sebastian", 1));
    }

    @Test
    void shouldReturnInconclusiveUsingFirstMethod() {
        assertEquals("INCONCLUSIVE", detectorService.tokenCheck("Nadia Jan Sebastian", 1));
    }

    //    SECOND METHOD TESTS
    @Test
    void shouldReturnFemaleUsingSecondMethod() {
        assertEquals("FEMALE", detectorService.tokenCheck("Jan Maria Rokita", 2));
    }

    @Test
    void shouldReturnMaleUsingSecondMethod() {
        assertEquals("MALE", detectorService.tokenCheck("Jan Sebastian Maria", 2));
    }

    @Test
    void shouldReturnInconclusiveUsingSecondMethod() {
        assertEquals("INCONCLUSIVE", detectorService.tokenCheck("Jan Maria Daniel", 2));
    }

    //     EXCEPTIONS TEST
    @Test
    void shouldThrowGuessMethodNotFound_WhenMethodOfGuessNotFound() {
        assertThrows(GuessMethodNotExistsException.class, () -> {
            detectorService.tokenCheck("Jan Maria Daniel", 3);
        });
    }

    @Test
    void shouldThrowParameterValidationException_WhenInputTokenEmpty() {
        assertThrows(ParameterValidationException.class, () -> {
            detectorService.tokenCheck("", 1);
        });
    }

    @Test
    void shouldThrowParameterValidationException_WhenGenderNotRecognized() {
        assertThrows(ParameterValidationException.class, () -> {
            detectorService.getAllByGender("NONE");
        });
    }

}