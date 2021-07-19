package com.distance.distancecalculator.service;

import com.distance.distancecalculator.model.User;
import com.distance.distancecalculator.utils.DistanceUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    DistanceCallerService distanceCallerService;

    @Mock
    DistanceUtils distanceUtils;

    @InjectMocks
    UserService userService;

    final String london = "London";
    final double londonLatitude = 51.509865;
    final double londonLongitude = -0.118092;

    @Test
    public void getUsersByRadiusToLondonWhenSingleDistanceInBoundsTest() {

        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                    "111.11.111.111", -6.5115909, 105.652983);

        User user2 = new User(002, "Jane", "Smith", "jane@test.com",
                    "22.22.222.222", -7.5115909, 106.652983);

        User user3 = new User(003, "John", "Doe", "John@test.com",
                    "333.33.333.333", -8.5115909, 108.652983);

        User user4 = new User(004, "Jane", "Doe", "jane@test.com",
                    "444.44.444.444", -9.5115909, 109.652983);

        // Train Mocks
        when(distanceCallerService.getUsers()).thenReturn(List.of(user1));
        when(distanceCallerService.getUsersByCity(eq(london))).thenReturn(List.of(user2, user3, user4));
        when(distanceUtils.calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()))).thenReturn(40.0);

        // Execute
        List<User> results = userService.getUsersByRadiusToLondon(50);

        // Assert
        assertEquals(4, results.size());

        // Living in radius of London
        assertEquals(001, results.get(3).getId());
        assertEquals("Jon", results.get(3).getFirstName());
        assertEquals("Smith", results.get(3).getLastName());
        assertEquals("jon@test.com", results.get(3).getEmail());
        assertEquals("111.11.111.111", results.get(3).getIpAddress());
        assertEquals(-6.5115909, results.get(3).getLatitude());
        assertEquals(105.652983, results.get(3).getLongitude());

        // Living in London
        assertEquals(002, results.get(0).getId());
        assertEquals("Jane", results.get(0).getFirstName());
        assertEquals("Smith", results.get(0).getLastName());
        assertEquals("jane@test.com", results.get(0).getEmail());
        assertEquals("22.22.222.222", results.get(0).getIpAddress());
        assertEquals(-7.5115909, results.get(0).getLatitude());
        assertEquals(106.652983, results.get(0).getLongitude());

        assertEquals(003, results.get(1).getId());
        assertEquals("John", results.get(1).getFirstName());
        assertEquals("Doe", results.get(1).getLastName());
        assertEquals("John@test.com", results.get(1).getEmail());
        assertEquals("333.33.333.333", results.get(1).getIpAddress());
        assertEquals(-8.5115909, results.get(1).getLatitude());
        assertEquals(108.652983, results.get(1).getLongitude());

        assertEquals(004, results.get(2).getId());
        assertEquals("Jane", results.get(2).getFirstName());
        assertEquals("Doe", results.get(2).getLastName());
        assertEquals("jane@test.com", results.get(2).getEmail());
        assertEquals("444.44.444.444", results.get(2).getIpAddress());
        assertEquals(-9.5115909, results.get(2).getLatitude());
        assertEquals(109.652983, results.get(2).getLongitude());

        verify(distanceCallerService, times(1)).getUsers();
        verify(distanceCallerService, times(1)).getUsersByCity(london);
        verify(distanceUtils, times(1)).calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()));
        verifyNoMoreInteractions(distanceCallerService);
        verifyNoMoreInteractions(distanceUtils);
    }

    @Test
    public void getUsersByRadiusToLondonWhenSingleDistanceOutOfBoundsTest() {

        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        User user2 = new User(002, "Jane", "Smith", "jane@test.com",
                "22.22.222.222", -7.5115909, 106.652983);

        User user3 = new User(003, "John", "Doe", "John@test.com",
                "333.33.333.333", -8.5115909, 108.652983);

        // Train Mocks
        when(distanceCallerService.getUsers()).thenReturn(List.of(user1, user2));
        when(distanceCallerService.getUsersByCity(eq(london))).thenReturn(List.of(user3));
        when(distanceUtils.calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()))).thenReturn(40.0);
        when(distanceUtils.calculateDistanceUsingHaversine(eq(londonLatitude), eq(user2.getLatitude()), eq(londonLongitude), eq(user2.getLongitude()))).thenReturn(60.0);

        // Execute
        List<User> results = userService.getUsersByRadiusToLondon(50);

        // Assert
        assertEquals(2, results.size());

        // Living in radius of London
        assertEquals(001, results.get(1).getId());
        assertEquals("Jon", results.get(1).getFirstName());
        assertEquals("Smith", results.get(1).getLastName());
        assertEquals("jon@test.com", results.get(1).getEmail());
        assertEquals("111.11.111.111", results.get(1).getIpAddress());
        assertEquals(-6.5115909, results.get(1).getLatitude());
        assertEquals(105.652983, results.get(1).getLongitude());

        // Living in London
        assertEquals(003, results.get(0).getId());
        assertEquals("John", results.get(0).getFirstName());
        assertEquals("Doe", results.get(0).getLastName());
        assertEquals("John@test.com", results.get(0).getEmail());
        assertEquals("333.33.333.333", results.get(0).getIpAddress());
        assertEquals(-8.5115909, results.get(0).getLatitude());
        assertEquals(108.652983, results.get(0).getLongitude());

        verify(distanceCallerService, times(1)).getUsers();
        verify(distanceCallerService, times(1)).getUsersByCity(london);
        verify(distanceUtils, times(1)).calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()));
        verify(distanceUtils, times(1)).calculateDistanceUsingHaversine(eq(londonLatitude), eq(user2.getLatitude()), eq(londonLongitude), eq(user2.getLongitude()));
        verifyNoMoreInteractions(distanceCallerService);
        verifyNoMoreInteractions(distanceUtils);
    }

    @Test
    public void getUsersByRadiusToLondonWhenNoUsersByCityTest() {
        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        // Train Mocks
        when(distanceCallerService.getUsers()).thenReturn(List.of(user1));
        when(distanceCallerService.getUsersByCity(eq(london))).thenReturn(List.of());
        when(distanceUtils.calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()))).thenReturn(40.0);

        // Execute
        List<User> results = userService.getUsersByRadiusToLondon(50);

        // Assert
        assertEquals(1, results.size());

        // Living in radius of London
        assertEquals(001, results.get(0).getId());
        assertEquals("Jon", results.get(0).getFirstName());
        assertEquals("Smith", results.get(0).getLastName());
        assertEquals("jon@test.com", results.get(0).getEmail());
        assertEquals("111.11.111.111", results.get(0).getIpAddress());
        assertEquals(-6.5115909, results.get(0).getLatitude());
        assertEquals(105.652983, results.get(0).getLongitude());

        verify(distanceCallerService, times(1)).getUsers();
        verify(distanceCallerService, times(1)).getUsersByCity(london);
        verify(distanceUtils, times(1)).calculateDistanceUsingHaversine(eq(londonLatitude), eq(user1.getLatitude()), eq(londonLongitude), eq(user1.getLongitude()));
        verifyNoMoreInteractions(distanceCallerService);
        verifyNoMoreInteractions(distanceUtils);
    }

    @Test
    public void getUsersByRadiusToLondonWhenNoUsersByRadiusTest() {
        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        // Train Mocks
        when(distanceCallerService.getUsers()).thenReturn(List.of());
        when(distanceCallerService.getUsersByCity(eq(london))).thenReturn(List.of(user1));

        // Execute
        List<User> results = userService.getUsersByRadiusToLondon(50);

        // Assert
        assertEquals(1, results.size());

        // Living in London
        assertEquals(001, results.get(0).getId());
        assertEquals("Jon", results.get(0).getFirstName());
        assertEquals("Smith", results.get(0).getLastName());
        assertEquals("jon@test.com", results.get(0).getEmail());
        assertEquals("111.11.111.111", results.get(0).getIpAddress());
        assertEquals(-6.5115909, results.get(0).getLatitude());
        assertEquals(105.652983, results.get(0).getLongitude());

        verify(distanceCallerService, times(1)).getUsers();
        verify(distanceCallerService, times(1)).getUsersByCity(london);
        verifyNoMoreInteractions(distanceCallerService);
        verifyNoInteractions(distanceUtils);
    }
}
