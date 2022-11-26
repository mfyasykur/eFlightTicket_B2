package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.CityEntity;

import java.util.List;

public interface CityService {

    CityEntity add(CityEntity cityEntity);
    CityEntity update(Long id, CityEntity cityEntity);
    Boolean delete(Long id);
    List<CityEntity> findAll();
    CityEntity findById(Long id);

    CityEntity addCity(Long countryId, CityEntity cityEntity);


    CityDTO mapToDto(CityEntity cityEntity);
    CityEntity mapToEntity(CityDTO cityDTO);
}
