package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.binar.eflightticket_b2.service.CityService;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    
    @Autowired
    CityRepository cityRepository;


    @Autowired
    CountryService countryService;
    
    @Override
    public City add(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City update(Long id, City city) {
        City result = findById(id);
        if (result != null) {
            result.setCityName(city.getCityName());
            result.setCityCode(city.getCityCode());
            result.setImageUrl(city.getImageUrl());
            result.setDescription(city.getDescription());
            return cityRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        City result = findById(id);
        if (result != null) {
            cityRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) {
        Optional<City> result = cityRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public City addCity(Long countryId, City city) {
        Country country = countryService.findById(countryId);
        if (country != null) {
            city = cityRepository.save(city);
            if (country.getCityEntities() != null) {
                List<City> cityEntities = country.getCityEntities();
                cityEntities.add(city);
                country.setCityEntities(cityEntities);
            }
            else{
                country.setCityEntities(Collections.singletonList(city));
            }
            countryService.add(country);
            return city;
        }
        return null;
    }


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public CityDTO mapToDto(City city) {
        return mapper.convertValue(city, CityDTO.class);
    }

    @Override
    public City mapToEntity(CityDTO cityDTO) {
        return mapper.convertValue(cityDTO, City.class);
    }
}
