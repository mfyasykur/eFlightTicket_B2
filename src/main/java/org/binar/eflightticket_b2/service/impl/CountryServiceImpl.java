package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Override
    public Country add(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update(Long id, Country country) {
        Country result = findById(id);
        if (result != null) {
            result.setCountryName(country.getCountryName());
            result.setCountryCode(country.getCountryCode());
            result.setImageUrl(country.getImageUrl());
            result.setDescription(country.getDescription());
            return countryRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Country result = findById(id);
        if (result != null) {
            countryRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        Optional<Country> result = countryRepository.findById(id);
        return result.orElse(null);
    }

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public CountryDTO mapToDto(Country country) {
        return mapper.convertValue(country, CountryDTO.class);
    }

    @Override
    public Country mapToEntity(CountryDTO countryDTO) {
        return mapper.convertValue(countryDTO, Country.class);
    }
}
