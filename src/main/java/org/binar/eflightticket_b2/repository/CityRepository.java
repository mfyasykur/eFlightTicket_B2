package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityCode(String cityCode);
}