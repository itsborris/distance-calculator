package com.distance.distancecalculator.service;

import com.distance.distancecalculator.model.User;
import com.distance.distancecalculator.utils.DistanceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserService {

    @Autowired
    DistanceCallerService distanceCallerService;

    @Autowired
    DistanceUtils distanceUtils;

    final String london = "London";
    final double londonLatitude = 51.509865;
    final double londonLongitude = -0.118092;

    public List<User> getUsersByRadiusToLondon(int maxRadius) {
        log.info("Processing request to get user by radius to London with max radius ({})", maxRadius);
        List<User> usersLivingInLondon = distanceCallerService.getUsersByCity(london);

        List<User> usersWithinRadiusToLondon = distanceCallerService.getUsers()
                .stream()
                .filter(user -> distanceUtils.calculateDistanceUsingHaversine(londonLatitude, user.getLatitude(), londonLongitude, user.getLongitude()) < maxRadius)
                .collect(Collectors.toList());

       return Stream.concat(usersLivingInLondon.stream(), usersWithinRadiusToLondon.stream())
                .collect(Collectors.toList());
    }
}