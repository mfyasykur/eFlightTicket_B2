package org.binar.eflightticket_b2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.entity.City;
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
    public Airport add(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Long id, Airport airport) {
        Airport result = findById(id);
        if (result != null) {
            result.setAirportName(airport.getAirportName());
            result.setAirportCode(airport.getAirportCode());
            return airportRepository.save(result);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Airport result = findById(id);
        if (result != null) {
            airportRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findById(Long id) {
        Optional<Airport> result = airportRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public Airport addAirport(Long cityId, Airport airport) {
        City city = cityService.findById(cityId);
        if (city != null) {
            airport = airportRepository.save(airport);
            if (city.getAirports() != null) {
                List<Airport> airportEntities = city.getAirports();
                airportEntities.add(airport);
                city.setAirports(airportEntities);
            }
            else{
                city.setAirports(Collections.singletonList(airport));
            }
            cityService.add(city);
            return airport;
        }
        return null;
    }


    ObjectMapper mapper = new ObjectMapper();

    @Override
    public AirportDTO mapToDto(Airport airport) {
        return mapper.convertValue(airport, AirportDTO.class);
    }

    @Override
    public Airport mapToEntity(AirportDTO airportDTO) {
        return mapper.convertValue(airportDTO, Airport.class);
    }

}
