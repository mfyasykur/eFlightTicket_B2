package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;

import java.util.List;

public interface CountryService {

    Country add(Country country);
    Country update(Long id, Country country);
    Boolean delete(Long id);
    List<Country> findAll();
    Country findById(Long id);

    CountryDTO mapToDto(Country country);
    Country mapToEntity(CountryDTO countryDTO);
}
