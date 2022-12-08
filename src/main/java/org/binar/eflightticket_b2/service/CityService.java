package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;

import java.util.List;

public interface CityService {

    City add(City city);
    City update(Long id, City city);
    City delete(Long id);
    List<City> findAll();
    City findById(Long id);

    City findByCityCode(String countryCode);

//    City addCity(Long countryId, City city);


    CityDTO mapToDto(City city);
    City mapToEntity(CityDTO cityDTO);
}