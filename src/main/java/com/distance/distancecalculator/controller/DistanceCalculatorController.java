package com.distance.distancecalculator.controller;

import com.distance.distancecalculator.model.User;
import com.distance.distancecalculator.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DistanceCalculatorController {

    @Autowired
    UserService userService;

    @GetMapping("/get-users-by-radius-to-london")
    public List<User> getUsersByRadiusToLondon (
            @RequestParam(value="radius", defaultValue="50") int radius
    ) {

        log.info("Request received to get users by radius to London with radius ({})", radius);

        return userService.getUsersByRadiusToLondon(radius);
    }
}
