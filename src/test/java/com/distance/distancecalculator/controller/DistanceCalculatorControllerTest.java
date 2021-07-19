package com.distance.distancecalculator.controller;

import com.distance.distancecalculator.model.User;
import com.distance.distancecalculator.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DistanceCalculatorController.class)
@ContextConfiguration(classes = DistanceCalculatorController.class)
public class DistanceCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUsersByRadiusToLondonWhenRadiusDefaultTest() throws Exception{
        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        // Train Mocks
        when(userService.getUsersByRadiusToLondon(50)).thenReturn(List.of(user1));

        // Execute and assert
        this.mockMvc.perform(get("/get-users-by-radius-to-london"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(001)))
                .andExpect(jsonPath("$[0].first_name", is("Jon")))
                .andExpect(jsonPath("$[0].last_name", is("Smith")))
                .andExpect(jsonPath("$[0].email", is("jon@test.com")))
                .andExpect(jsonPath("$[0].ip_address", is("111.11.111.111")))
                .andExpect(jsonPath("$[0].latitude", is(-6.5115909)))
                .andExpect(jsonPath("$[0].longitude", is(105.652983)));

        verify(userService, times(1)).getUsersByRadiusToLondon(eq(50));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUsersByRadiusToLondonWhenRadiusProvidedTest() throws Exception {
        // Setup test data
        User user1 = new User(001, "Jon", "Smith", "jon@test.com",
                "111.11.111.111", -6.5115909, 105.652983);

        // Train Mocks
        when(userService.getUsersByRadiusToLondon(60)).thenReturn(List.of(user1));

        // Execute and assert
        this.mockMvc.perform(get("/get-users-by-radius-to-london?radius=60"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(001)))
                .andExpect(jsonPath("$[0].first_name", is("Jon")))
                .andExpect(jsonPath("$[0].last_name", is("Smith")))
                .andExpect(jsonPath("$[0].email", is("jon@test.com")))
                .andExpect(jsonPath("$[0].ip_address", is("111.11.111.111")))
                .andExpect(jsonPath("$[0].latitude", is(-6.5115909)))
                .andExpect(jsonPath("$[0].longitude", is(105.652983)));

        verify(userService, times(1)).getUsersByRadiusToLondon(eq(60));
        verifyNoMoreInteractions(userService);
    }
}
