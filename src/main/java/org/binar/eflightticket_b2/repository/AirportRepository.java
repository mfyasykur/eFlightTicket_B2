package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
//    Airport findByAirportCode(String airportCode);
}
