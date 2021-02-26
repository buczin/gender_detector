package com.gender.detector.service;

import java.util.List;

/**
 * The Service which contains business logic for Detector.
 */
public interface DetectorService {

    /**
     * Returns all names by given gender.
     *
     * @return List of names
     */
    List<String> getAllByGender(String gender);

    /**
     * Returns detected gender by given token.
     *
     * @return gender
     */
    String tokenCheck(String token, int method);

    /**
     * Returns detected gender by given token.
     * Checking only first name from token.
     *
     * @return gender
     */
    String onlyFirstTokenCheck(String token);

    /**
     * Returns detected gender by given token.
     * Checking all names from token.
     *
     * @return gender
     */
    String allTokensCheck(String token);

}
