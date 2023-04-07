package com.example.tqs_116726_hw1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    LocationEntity findByLocationNameAndRequestDateAndHit(String locationName, LocalDate now, boolean bool);
}
