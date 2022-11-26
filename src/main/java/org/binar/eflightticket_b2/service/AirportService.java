package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.AirportEntity;

import java.util.List;

public interface AirportService {
    AirportEntity add(AirportEntity airportEntity);
    AirportEntity update(Long id, AirportEntity airportEntity);
    Boolean delete(Long id);
    List<AirportEntity> findAll();
    AirportEntity findById(Long id);

    AirportEntity addAirport(Long cityId, AirportEntity airportEntity);


    AirportDTO mapToDto(AirportEntity airportEntity);
    AirportEntity mapToEntity(AirportDTO airportDTO);
}
