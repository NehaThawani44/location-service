package com.jysk.project.location.controller;


import com.jysk.project.location.domain.Location;
import com.jysk.project.location.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



import java.util.List;


@Controller
@Slf4j
public class LocationController {

    @Autowired
    private LocationService locationService;


    @PostMapping("/add")
    public ResponseEntity<String> addLocation(@RequestBody Location location) {
        locationService.addLocation(location);
        return ResponseEntity.ok("Location added successfully");
    }

    @GetMapping("/")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/zipcode/{zipCode}")
    public ResponseEntity<Location> getLocationByZipCode(@PathVariable String zipCode) {
        Location location = locationService.getLocationByZipCode(zipCode);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<String> addMultipleLocations(@RequestBody List<Location> locations) {
        locationService.addMultipleLocations(locations);
        return ResponseEntity.ok("Locations added successfully");
    }

    @PutMapping("/update/{zipCode}")
    public ResponseEntity<Location> updateCityName(@PathVariable String zipCode, @RequestBody Location location) {
        Location updatedLocation = locationService.updateCityName(zipCode, location.getCityName());
        if (updatedLocation != null) {
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/delete/{zipCode}")
    public ResponseEntity<String> deleteLocation(@PathVariable String zipCode) {

        if (zipCode!=null) {
            locationService.deleteLocation(zipCode);
            return ResponseEntity.ok("Location deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Location not found for zip code: " + zipCode);
        }
    }

}


