package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.CityEntity;
import org.binar.eflightticket_b2.entity.CountryEntity;
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
    public CityEntity add(CityEntity cityEntity) {
        return cityRepository.save(cityEntity);
    }

    @Override
    public CityEntity update(Long id, CityEntity cityEntity) {
        CityEntity result = findById(id);
        if (result != null) {
            result.setCityName(cityEntity.getCityName());
            result.setCityCode(cityEntity.getCityCode());
            result.setImageUrl(cityEntity.getImageUrl());
            result.setDescription(cityEntity.getDescription());
            return cityRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        CityEntity result = findById(id);
        if (result != null) {
            cityRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<CityEntity> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public CityEntity findById(Long id) {
        Optional<CityEntity> result = cityRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public CityEntity addCity(Long countryId, CityEntity cityEntity) {
        CountryEntity countryEntity = countryService.findById(countryId);
        if (countryEntity != null) {
            cityEntity = cityRepository.save(cityEntity);
            if (countryEntity.getCityEntities() != null) {
                List<CityEntity> cityEntities = countryEntity.getCityEntities();
                cityEntities.add(cityEntity);
                countryEntity.setCityEntities(cityEntities);
            }
            else{
                countryEntity.setCityEntities(Collections.singletonList(cityEntity));
            }
            countryService.add(countryEntity);
            return cityEntity;
        }
        return null;
    }


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public CityDTO mapToDto(CityEntity cityEntity) {
        return mapper.convertValue(cityEntity, CityDTO.class);
    }

    @Override
    public CityEntity mapToEntity(CityDTO cityDTO) {
        return mapper.convertValue(cityDTO, CityEntity.class);
    }
}
