package com.jysk.project.location.service;

import com.jysk.project.location.domain.Location;
import com.jysk.project.location.exception.CustomException;
import com.jysk.project.location.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    private Map<String, Location> zipCodeToLocationMap = new HashMap<>();
    private Map<String, Location> cityNameToLocationMap = new HashMap<>();

   /* @PostConstruct
    public void init() {
        // Initialize the maps of locations
        addLocation(new Location("21079", "Harburg"));
        addLocation(new Location("60311", "Frankfurt"));
        addLocation(new Location("20144", "Hamburg"));
        addLocation(new Location("53111", "Bonn"));
        addLocation(new Location("24976", "Handewitt"));
        addLocation(new Location("24558", "Flensburg"));
    }*/

    public Location searchLocation(String query) {
        Location location = locationRepository.findByZipCode(query);

        if (location == null) {
            location = locationRepository.findByCityName(query);
        }
        return location;
    }

    public void addLocation(Location location) {
        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            log.error("Error adding location: ZIP code must be unique", e);
            throw new CustomException("ZIP code must be unique");
        }
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationByZipCode(String zipCode) {
        return locationRepository.findByZipCode(zipCode);
    }

    public void addMultipleLocations(List<Location> locations) {
        locationRepository.saveAll(locations);
    }

    public Location updateCityName(String zipCode, String cityName) {
        Location location = locationRepository.findByZipCode(zipCode);
        if (location != null) {
            location.setCityName(cityName);
            return locationRepository.save(location);
        }
        throw new CustomException("Location not found");
    }


    public void deleteLocation(String zipCode) {
        Location location = locationRepository.findByZipCode(zipCode);
        if (location != null) {
            locationRepository.delete(location);
        } else {
            throw new CustomException("Location not found");
        }
    }

}

