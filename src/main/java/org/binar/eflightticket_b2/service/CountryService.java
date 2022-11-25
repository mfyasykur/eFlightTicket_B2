package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.CountryEntity;

public interface CountryService {

    CountryEntity add(CountryEntity countryEntity);
    
    CountryDTO mapToDto(CountryEntity countryEntity);
    CountryEntity mapToEntity(CountryDTO countryDTO);
}
