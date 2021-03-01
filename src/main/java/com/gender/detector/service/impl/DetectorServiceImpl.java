package com.gender.detector.service.impl;

import com.gender.detector.exceptions.GuessMethodNotExistsException;
import com.gender.detector.exceptions.ParameterValidationException;
import com.gender.detector.service.DetectorService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public final class DetectorServiceImpl implements DetectorService {

    @Override
    public List<String> getAllByGender(String gender) {
        if (gender.equalsIgnoreCase("MALE")) {
            return getFileContent("male");
        } else if (gender.equalsIgnoreCase("FEMALE")) {
            return getFileContent("female");
        }
        throw new ParameterValidationException();
    }

    @Override
    public String tokenCheck(String token, int method) {
        validateToken(token);
        if (method == 1) return onlyFirstTokenCheck(token.toUpperCase());
        if (method == 2) return allTokensCheck(token.toUpperCase());
        throw new GuessMethodNotExistsException();
    }

    @Override
    public String onlyFirstTokenCheck(String token) {
        String[] arrayToken = token.split(" ");

        // Detect first name from input
        return searchInFile(arrayToken[0]);
    }

    @Override
    public String allTokensCheck(String token) {
        String[] arrayToken = token.split(" ");
        List<String> genderList = new ArrayList<>();

        // Add to the list all detected genders from the input token
        for (String s : arrayToken) {
            genderList.add(searchInFile(s));
        }

        // Counting the genders identified from the token
        LinkedHashMap<String, Long> counted = genderList.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting())
        ).entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        // Checking if the number of occurrences is the same or more and return result
        Iterator<Map.Entry<String, Long>> it = counted.entrySet().iterator();
        if (counted.size() > 1) {
            Map.Entry<String, Long> n1 = it.next();
            Map.Entry<String, Long> n2 = it.next();
            if (n1.getValue().equals(n2.getValue())) {
                return "INCONCLUSIVE";
            } else {
                return n1.getKey();
            }
        } else {
            return it.next().getKey();
        }
    }


    private String searchInFile(String token) {
        String gender = "INCONCLUSIVE";
        boolean found = false;


        Scanner scMale = new Scanner(getInputStreamOfGender("male"), StandardCharsets.UTF_8);
        while (scMale.hasNextLine()) {
            String line = scMale.nextLine();
            if (line.toUpperCase().matches(token)) {
                gender = "MALE";
                found = true;
                break;
            }
        }

        if (!found) {
            Scanner scFemale = new Scanner(getInputStreamOfGender("female"), StandardCharsets.UTF_8);
            while (scFemale.hasNextLine()) {
                String line = scFemale.nextLine();
                if (line.toUpperCase().matches(token)) {
                    gender = "FEMALE";
                    break;
                }
            }
        }

        return gender;
    }

    private List<String> getFileContent(String gender) {
        return new BufferedReader(new InputStreamReader(getInputStreamOfGender(gender))).lines().collect(Collectors.toList());
    }

    private InputStream getInputStreamOfGender(String gender) {
        InputStream is = null;
        is = DetectorServiceImpl.class.getClassLoader().getResourceAsStream("names\\" + gender + ".txt");
        if (is == null) {
            Resource resource = new ClassPathResource("classpath:names/" + gender + ".txt");
            try {
                is = resource.getInputStream();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return is;
        } else {
            return is;
        }
    }

    private void validateToken(String token) {
        if (token.isEmpty()) throw new ParameterValidationException();
    }

}
