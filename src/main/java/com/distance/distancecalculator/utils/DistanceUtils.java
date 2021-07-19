package com.distance.distancecalculator.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.Math.round;

@Slf4j
@Service
public class DistanceUtils {

    private final double earthRadius = 3956;

    public double calculateDistanceUsingHaversine (double latOne, double latTwo, double lonOne, double lonTwo) {
        log.debug("Calculating distance with latOne ({}), latTwo ({}), lonOne ({{}). lonTwo ({})", latOne, latTwo, lonOne, lonTwo);

        // convert degrees to radians
        lonOne = Math.toRadians(lonOne);
        lonTwo = Math.toRadians(lonTwo);
        latOne = Math.toRadians(latOne);
        latTwo = Math.toRadians(latTwo);

        // execute the Haversine formula
        double deltaLon = lonTwo - lonOne;
        double deltaLat = latTwo - latOne;
        double formula = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(latOne) * Math.cos(latTwo)
                * Math.pow(Math.sin(deltaLon / 2),2);
        double fOutput = 2 * Math.asin(Math.sqrt(formula));

        return(round(fOutput * earthRadius));

    }
}
