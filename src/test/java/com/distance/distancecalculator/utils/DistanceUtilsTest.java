package com.distance.distancecalculator.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class DistanceUtilsTest {

    @InjectMocks
    DistanceUtils distanceUtils;

    final double londonLatitude = 51.509865;
    final double londonLongitude = -0.118092;
    final double wembleyLatitude = 51.550503;
    final double wembleyLongitude = -0.304841;


    @Test
    public void calculateDistanceUsingHaversineTest() {
        double result = distanceUtils.calculateDistanceUsingHaversine(londonLatitude, wembleyLatitude, londonLongitude, wembleyLongitude);

        assertEquals(8.0, result);
    }
}
