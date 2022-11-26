package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.AirportEntity;
import org.binar.eflightticket_b2.entity.CityEntity;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.service.AirportService;
import org.binar.eflightticket_b2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {

    @Autowired
    AirportRepository airportRepository;


    @Autowired
    CityService cityService;

    @Override
    public AirportEntity add(AirportEntity airportEntity) {
        return airportRepository.save(airportEntity);
    }

    @Override
    public AirportEntity update(Long id, AirportEntity airportEntity) {
        AirportEntity result = findById(id);
        if (result != null) {
            result.setAirportName(airportEntity.getAirportName());
            result.setAirportCode(airportEntity.getAirportCode());
            return airportRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        AirportEntity result = findById(id);
        if (result != null) {
            airportRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<AirportEntity> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public AirportEntity findById(Long id) {
        Optional<AirportEntity> result = airportRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public AirportEntity addAirport(Long cityId, AirportEntity airportEntity) {
        CityEntity cityEntity = cityService.findById(cityId);
        if (cityEntity != null) {
            airportEntity = airportRepository.save(airportEntity);
            if (cityEntity.getAirportEntities() != null) {
                List<AirportEntity> airportEntities = cityEntity.getAirportEntities();
                airportEntities.add(airportEntity);
                cityEntity.setAirportEntities(airportEntities);
            }
            else{
                cityEntity.setAirportEntities(Collections.singletonList(airportEntity));
            }
            cityService.add(cityEntity);
            return airportEntity;
        }
        return null;
    }


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public AirportDTO mapToDto(AirportEntity airportEntity) {
        return mapper.convertValue(airportEntity, AirportDTO.class);
    }

    @Override
    public AirportEntity mapToEntity(AirportDTO airportDTO) {
        return mapper.convertValue(airportDTO, AirportEntity.class);
    }

}
