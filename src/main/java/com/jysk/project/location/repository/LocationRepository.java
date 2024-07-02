package com.jysk.project.location.repository;

import com.jysk.project.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
    Location findByZipCode(String zipCode);
    Location findByCityName(String cityName);
}
