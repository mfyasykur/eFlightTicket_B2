package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.entity.Aircraft;

import java.util.List;

public interface AircraftService {

    Aircraft addAircraft(Aircraft aircraft);
    Aircraft updateAircraft(Long id, Aircraft aircraft);
    Aircraft deleteAircraft(Long id);
    List<Aircraft> getAllAircraft();
    Aircraft getAircraftById(Long id);

    AircraftDTO mapToDto(Aircraft aircraft);
    Aircraft mapToEntity(AircraftDTO aircraftDTO);
}
