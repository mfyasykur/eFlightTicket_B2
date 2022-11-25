package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.CountryEntity;

import java.util.List;

public interface CountryService {

    CountryEntity add(CountryEntity countryEntity);
    CountryEntity update(Long id, CountryEntity countryEntity);
    Boolean delete(Long id);
    List<CountryEntity> findAll();
    CountryEntity findById(Long id);

    
    CountryDTO mapToDto(CountryEntity countryEntity);
    CountryEntity mapToEntity(CountryDTO countryDTO);
}
