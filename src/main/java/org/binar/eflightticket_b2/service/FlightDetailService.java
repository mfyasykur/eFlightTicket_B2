package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.FlightDetailDTO;
import org.binar.eflightticket_b2.entity.FlightDetail;

import java.util.List;

public interface FlightDetailService {

    FlightDetail addFlightDetail(Long id);
//    FlightDetail updateFlightDetail(Long id, FlightDetail flightDetail);
    FlightDetail deleteFlightDetail(Long id);
    List<FlightDetail> getAllFlightDetails();
    FlightDetail getFlightDetailById(Long id);

//    List<FlightDetailDTO> getAll();
//    FlightDetailDTO add(Long id);

    FlightDetailDTO mapToDto(FlightDetail flightDetail);
    FlightDetail mapToEntity(FlightDetailDTO flightDetailDTO);
}
