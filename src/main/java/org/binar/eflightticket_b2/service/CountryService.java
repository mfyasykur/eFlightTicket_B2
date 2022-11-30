package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;

import java.util.List;

public interface CountryService {

    Country add(Country country);
    Country update(Long id, Country country);
    Country delete(Long id);
    List<Country> findAll();
    Country findById(Long id);

    Country findByCountryCode(String countryCode);

    CountryDTO mapToDto(Country country);
    Country mapToEntity(CountryDTO countryDTO);
}
