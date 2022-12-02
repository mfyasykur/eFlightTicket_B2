package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private static final String ENTITY = "country";
    @Autowired
    CountryRepository countryRepository;
    @Override
    public Country add(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update(Long id, Country country) {
        Country result = countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setCountryName(country.getCountryName());
        result.setCountryCode(country.getCountryCode());
        result.setImageUrl(country.getImageUrl());
        result.setDescription(country.getDescription());
        countryRepository.save(result);
        return result;
    }

    @Override
    public Country delete(Long id) {
        Country result = countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        countryRepository.delete(result);
        return result;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
    }

    @Override
    public Country findByCountryCode(String countryCode) {
        Country byCountryCode = countryRepository.findByCountryCode(countryCode);
        if (byCountryCode != null) {
            return byCountryCode;
        }
        ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "countryCode", countryCode);
        exception.setApiResponse();
        throw exception;
    }
    ModelMapper mapper = new ModelMapper();

    @Override
    public CountryDTO mapToDto(Country country) {
        return mapper.map(country, CountryDTO.class);
    }

    @Override
    public Country mapToEntity(CountryDTO countryDTO) {
        return mapper.map(countryDTO, Country.class);
    }
}