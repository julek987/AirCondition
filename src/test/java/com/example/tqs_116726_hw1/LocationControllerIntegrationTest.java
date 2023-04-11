package com.example.tqs_116726_hw1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @Test
    public void testIndex() throws Exception {
        when(locationService.location()).thenReturn("Location");
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Location"));
    }

    @Test
    public void testUserRequestedLocationData() throws Exception {
        Location mockLocation = new Location("Porto", "21", "37", "1", "2", "3");
        when(locationService.userRequestedLocationData("Porto")).thenReturn(mockLocation);

        MvcResult result = mockMvc.perform(get("/location/Porto"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Location responseLocation = new ObjectMapper().readValue(jsonResponse, Location.class);
        assertEquals(mockLocation, responseLocation);
    }

    @Test
    public void testNumberOfRequestAndHits() throws Exception {
        Map<String, Integer> mockMap = new HashMap<>();
        mockMap.put("Hits", 10);
        mockMap.put("Requests", 20);

        when(locationService.getNumberOfRequestsAndHits()).thenReturn(Arrays.asList(10, 20));

        MvcResult result = mockMvc.perform(get("/data/stats"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Map<String, Integer> responseMap = new ObjectMapper().readValue(jsonResponse, new TypeReference<Map<String, Integer>>() {});
        assertEquals(mockMap, responseMap);
    }
}
