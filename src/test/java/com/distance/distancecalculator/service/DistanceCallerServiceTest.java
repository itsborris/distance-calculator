package com.distance.distancecalculator.service;

import com.distance.distancecalculator.exception.DistanceCallerException;
import com.distance.distancecalculator.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DistanceCallerServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    DistanceCallerService distanceCallerService;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(distanceCallerService, "userApiUrl", "https://mock.com");
    }

    @Test
    public void getUsersTest() {

        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        User user2 = new User(002, "Jane", "Smith", "jane@test.com",
                "22.22.222.222", -7.5115909, 106.652983);

        User mockUsers[] = new User[] { user1, user2 };

        // Train mocks
        when(restTemplate.getForEntity(any(String.class), eq(User[].class))).thenReturn(new ResponseEntity(mockUsers, HttpStatus.OK));

        // Execute
        List<User> result = distanceCallerService.getUsers();

        // Assert
        assertEquals(001, result.get(0).getId());
        assertEquals("Jon", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
        assertEquals("jon@test.com", result.get(0).getEmail());
        assertEquals("111.11.111.111", result.get(0).getIpAddress());
        assertEquals(-6.5115909, result.get(0).getLatitude());
        assertEquals(105.652983, result.get(0).getLongitude());

        assertEquals(002, result.get(1).getId());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("jane@test.com", result.get(1).getEmail());
        assertEquals("22.22.222.222", result.get(1).getIpAddress());
        assertEquals(-7.5115909, result.get(1).getLatitude());
        assertEquals(106.652983, result.get(1).getLongitude());

        verify(restTemplate, times(1)).getForEntity(eq("https://mock.com/users"), eq(User[].class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test()
    public void getUsersWhenExceptionThrownTest() {

        // Train mocks
        when(restTemplate.getForEntity(any(String.class), eq(User[].class))).thenThrow(new RuntimeException());

        // Execute
        Exception exception = assertThrows(DistanceCallerException.class, () -> {
            distanceCallerService.getUsers();
        });

        assertTrue(exception.getMessage().contains("Error getting all users from API"));
    }

    @Test
    public void getUsersByCity() {

        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        User user2 = new User(002, "Jane", "Smith", "jane@test.com",
                "22.22.222.222", -7.5115909, 106.652983);

        User mockUsers[] = new User[] { user1, user2 };

        // Train mocks
        when(restTemplate.getForEntity(any(String.class), eq(User[].class))).thenReturn(new ResponseEntity(mockUsers, HttpStatus.OK));

        // Execute
        List<User> result = distanceCallerService.getUsersByCity("London");

        // Assert
        assertEquals(001, result.get(0).getId());
        assertEquals("Jon", result.get(0).getFirstName());
        assertEquals("Smith", result.get(0).getLastName());
        assertEquals("jon@test.com", result.get(0).getEmail());
        assertEquals("111.11.111.111", result.get(0).getIpAddress());
        assertEquals(-6.5115909, result.get(0).getLatitude());
        assertEquals(105.652983, result.get(0).getLongitude());

        assertEquals(002, result.get(1).getId());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("jane@test.com", result.get(1).getEmail());
        assertEquals("22.22.222.222", result.get(1).getIpAddress());
        assertEquals(-7.5115909, result.get(1).getLatitude());
        assertEquals(106.652983, result.get(1).getLongitude());

        verify(restTemplate, times(1)).getForEntity(eq("https://mock.com/city/London/users"), eq(User[].class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test()
    public void getUsersByCityWhenExceptionThrownTest() {

        // Train mocks
        when(restTemplate.getForEntity(any(String.class), eq(User[].class))).thenThrow(new RuntimeException());

        // Execute
        Exception exception = assertThrows(DistanceCallerException.class, () -> {
            distanceCallerService.getUsersByCity("London");
        });

        assertTrue(exception.getMessage().contains("Error getting users by city from API"));
    }
}
