package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.CountryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private static final String ENTITY = "country";
    private final Logger log =  LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country add(Country country) {
        log.info("Has successfully created country data!");
        return countryRepository.save(country);
    }

    @Override
    public Country update(Long id, Country country) {
        Country result = countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });

        result.setCountryName(country.getCountryName());
        result.setCountryCode(country.getCountryCode());
        result.setImageUrl(country.getImageUrl());
        result.setDescription(country.getDescription());
        countryRepository.save(result);
        log.info("Has successfully updated country data!");
        return result;
    }

    @Override
    public Country delete(Long id) {
        Country result = countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        countryRepository.delete(result);
        log.info("Has successfully deleted country data!");
        return result;
    }

    @Override
    public List<Country> findAll() {
        log.info("Has successfully found all country data!");
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found country data from id " + id);
        return country;
    }

    @Override
    public Country findByCountryCode(String countryCode) {
        Country byCountryCode = countryRepository.findCountryByCode(countryCode)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "countryCode", countryCode);
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found country data from code " + countryCode);
        return byCountryCode;
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