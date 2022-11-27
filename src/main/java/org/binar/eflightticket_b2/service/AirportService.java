package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;

import java.util.List;

public interface AirportService {
    Airport add(Airport airport);
    Airport update(Long id, Airport airport);
    Boolean delete(Long id);
    List<Airport> findAll();
    Airport findById(Long id);

    Airport addAirport(Long cityId, Airport airport);


    AirportDTO mapToDto(Airport airport);
    Airport mapToEntity(AirportDTO airportDTO);
}
