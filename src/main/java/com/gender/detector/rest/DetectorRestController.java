package com.gender.detector.rest;

import com.gender.detector.service.DetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/detector")
public class DetectorRestController {

    @Autowired
    DetectorService detectorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAll(@RequestParam String gender) {
        return detectorService.getAllByGender(gender);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{method}/guess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> tokenCheck(@PathVariable int method, @RequestParam String name) {
        Map<String, String> map = new HashMap<>();
        map.put("result", detectorService.tokenCheck(name, method));
        return map;
    }

}
