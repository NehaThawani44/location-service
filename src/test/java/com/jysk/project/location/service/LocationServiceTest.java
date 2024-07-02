package com.jysk.project.location.service;

import com.jysk.project.location.domain.Location;
import com.jysk.project.location.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LocationServiceTest {


    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchLocationByZipCode() {
        Location location = new Location("12345", "New York");
        when(locationRepository.findByZipCode("12345")).thenReturn(location);
        Location result = locationService.searchLocation("12345");
        assertEquals("New York", result.getCityName());
    }

    @Test
    void testSearchLocationByCityName() {
        Location location = new Location("12345", "New York");
        when(locationRepository.findByCityName("New York")).thenReturn(location);
        Location result = locationService.searchLocation("New York");
        assertEquals("12345", result.getZipCode());
    }

    @Test
    void testAddLocation() {
        Location location = new Location("12345", "New York");
        locationService.addLocation(location);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testAddMultipleLocations() {
        List<Location> locations = Arrays.asList(new Location("12345", "New York"), new Location("90210", "Beverly Hills"));
        locationService.addMultipleLocations(locations);
        verify(locationRepository, times(1)).saveAll(locations);
    }

    @Test
    void testGetAllLocations() {
        List<Location> locations = Arrays.asList(new Location("12345", "New York"), new Location("90210", "Beverly Hills"));
        when(locationRepository.findAll()).thenReturn(locations);
        List<Location> result = locationService.getAllLocations();
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateCityName() {
        Location location = new Location("12345", "Old York");
        when(locationRepository.findByZipCode("12345")).thenReturn(location);
        Location updatedLocation = new Location("12345", "New York");
        when(locationRepository.save(location)).thenReturn(updatedLocation);
        Location result = locationService.updateCityName("12345", "New York");
        assertEquals("New York", result.getCityName());
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location("12345", "New York");
        when(locationRepository.findByZipCode("12345")).thenReturn(location);
        boolean result = locationService.deleteLocation("12345");
        verify(locationRepository, times(1)).delete(location);
        assertTrue(result);
    }
}



