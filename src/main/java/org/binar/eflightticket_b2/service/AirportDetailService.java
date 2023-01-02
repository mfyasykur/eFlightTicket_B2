package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.AirportDetailDTO;
import org.binar.eflightticket_b2.entity.AirportDetail;

import java.util.List;

public interface AirportDetailService {
    AirportDetail add(Long countryId, Long cityId, Long airportId);
    AirportDetail delete(Long id);
    List<AirportDetail> findAll();
    AirportDetail findById(Long id);


    AirportDetailDTO mapToDto(AirportDetail airportDetail);
    AirportDetail mapToEntity(AirportDetailDTO airportDetailDTO);

}
