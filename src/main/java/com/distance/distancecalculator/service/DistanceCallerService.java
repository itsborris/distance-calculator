package com.distance.distancecalculator.service;

import com.distance.distancecalculator.exception.DistanceCallerException;
import com.distance.distancecalculator.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class DistanceCallerService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${configuration.user-api-url}")
    private String userApiUrl;

    public List<User> getUsers() {
        log.info("Getting users from API");

        try {
            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(userApiUrl + "/users", User[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch(Exception e) {
            log.error("Error while calling external API to get users by city: " + e);
            throw new DistanceCallerException("Error getting all users from API", e);
        }
    }

    public List<User> getUsersByCity(String city) {
        log.info("Getting users from API where city is ({})", city);

        try {
            ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(userApiUrl + "/city/" + city +"/users", User[].class);
            return Arrays.asList(responseEntity.getBody());
        } catch(Exception e) {
            log.error("Error while calling external API to get users by city: " + e);
            throw new DistanceCallerException("Error getting users by city from API", e);
        }
    }
}
