package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.CountryEntity;
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
    public CountryEntity add(CountryEntity countryEntity) {
        return countryRepository.save(countryEntity);
    }

    @Override
    public CountryEntity update(Long id, CountryEntity countryEntity) {
        CountryEntity result = findById(id);
        if (result != null) {
            result.setCountryName(countryEntity.getCountryName());
            result.setCountryCode(countryEntity.getCountryCode());
            result.setImageUrl(countryEntity.getImageUrl());
            result.setDescription(countryEntity.getDescription());
            return countryRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        CountryEntity result = findById(id);
        if (result != null) {
            countryRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<CountryEntity> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public CountryEntity findById(Long id) {
        Optional<CountryEntity> result = countryRepository.findById(id);
        return result.orElse(null);
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
