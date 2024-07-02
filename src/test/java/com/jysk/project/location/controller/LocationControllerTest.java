package com.jysk.project.location.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jysk.project.location.domain.Location;
import com.jysk.project.location.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LocationControllerTest {


    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    void testSearchPage() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(content().string("Use POST /locations/search with a 'query' parameter to search for a location."));
    }

    @Test
    void testSearchLocation() throws Exception {
        Location location = new Location("12345", "New York");
        when(locationService.searchLocation("12345")).thenReturn(location);

        mockMvc.perform(post("/search")
                        .param("query", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("New York"));
    }

    @Test
    void testAddLocation() throws Exception {
        Location location = new Location("12345", "New York");

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isOk())
                .andExpect(content().string("Location added successfully"));

        verify(locationService, times(1)).addLocation(any(Location.class));
    }

    @Test
    void testAddMultipleLocations() throws Exception {
        Location location1 = new Location("12345", "New York");
        Location location2 = new Location("90210", "Beverly Hills");

        mockMvc.perform(post("/addMultiple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Arrays.asList(location1, location2))))
                .andExpect(status().isOk())
                .andExpect(content().string("Locations added successfully"));

        verify(locationService, times(1)).addMultipleLocations(anyList());
    }

    @Test
    void testGetAllLocations() throws Exception {
        Location location1 = new Location("12345", "New York");
        Location location2 = new Location("90210", "Beverly Hills");
        when(locationService.getAllLocations()).thenReturn(Arrays.asList(location1, location2));

        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cityName").value("New York"))
                .andExpect(jsonPath("$[1].cityName").value("Beverly Hills"));
    }

    @Test
    void testGetLocationByZipCode() throws Exception {
        Location location = new Location("12345", "New York");
        when(locationService.getLocationByZipCode("12345")).thenReturn(location);

        mockMvc.perform(get("/zipcode/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("New York"));
    }

    @Test
    void testUpdateCityName() throws Exception {
        Location location = new Location("12345", "New York");
        when(locationService.updateCityName(eq("12345"), anyString())).thenReturn(location);

        mockMvc.perform(put("/update/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("New York"));
    }

    @Test
    void testDeleteLocation() throws Exception {
        when(locationService.deleteLocation("12345")).thenReturn(true);

        mockMvc.perform(delete("/delete/12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Location deleted successfully"));
    }

}
