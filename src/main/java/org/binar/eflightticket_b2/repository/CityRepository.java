package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
//    City findByCityCode(String cityCode);
}