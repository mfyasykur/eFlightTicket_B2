package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.CountryEntity;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {


    @Autowired
    CountryRepository countryRepository;
    @Override
    public CountryEntity add(CountryEntity countryEntity) {
        return countryRepository.save(countryEntity);
    }

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public CountryDTO mapToDto(CountryEntity countryEntity) {
        return mapper.convertValue(countryEntity, CountryDTO.class);
    }

    @Override
    public CountryEntity mapToEntity(CountryDTO countryDTO) {
        return mapper.convertValue(countryDTO, CountryEntity.class);
    }
}
