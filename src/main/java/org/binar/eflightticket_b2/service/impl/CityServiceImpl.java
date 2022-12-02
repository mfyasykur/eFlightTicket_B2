package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.binar.eflightticket_b2.service.CityService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private static final String ENTITY = "city";
    private final Logger log =  LoggerFactory.getLogger(CityServiceImpl.class);
    @Autowired
    CityRepository cityRepository;

    @Override
    public City add(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City update(Long id, City city) {
        City result = cityRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setCityName(city.getCityName());
        result.setCityCode(city.getCityCode());
        result.setImageUrl(city.getImageUrl());
        result.setDescription(city.getDescription());
        cityRepository.save(result);
        return result;
    }

    @Override
    public City delete(Long id) {
        City result = cityRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        cityRepository.delete(result);
        return result;
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
    }

    @Override
    public City findByCityCode(String cityCode) {
        City byCityCode = cityRepository.findByCityCode(cityCode);
        if (byCityCode != null) {
            return byCityCode;
        }
        ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "cityCode", cityCode);
        exception.setApiResponse();
        throw exception;
    }
    ModelMapper mapper = new ModelMapper();

    @Override
    public CityDTO mapToDto(City city) {
        return mapper.map(city, CityDTO.class);
    }

    @Override
    public City mapToEntity(CityDTO cityDTO) {
        return mapper.map(cityDTO, City.class);
    }
}