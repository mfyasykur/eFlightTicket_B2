package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.service.AirportService;
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
public class AirportServiceImpl implements AirportService {

    private static final String ENTITY = "airport";

    private final Logger log =  LoggerFactory.getLogger(AirportServiceImpl.class);

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
        Airport result = airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setAirportName(airport.getAirportName());
        result.setAirportCode(airport.getAirportCode());
        return result;
    }

    @Override
    public Airport delete(Long id) {
        Airport result = airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        airportRepository.delete(result);
        return result;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
    }

    @Override
    public Airport findByAirportCode(String airportCode) {
        Airport byAirportCode = airportRepository.findByAirportCode(airportCode);
        if (byAirportCode != null) {
            return byAirportCode;
        }
        ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "airportCode", airportCode);
        exception.setApiResponse();
        throw exception;
    }

    ModelMapper mapper = new ModelMapper();

    @Override
    public AirportDTO mapToDto(Airport airport) {
        return mapper.map(airport, AirportDTO.class);
    }

    @Override
    public Airport mapToEntity(AirportDTO airportDTO) {
        return mapper.map(airportDTO, Airport.class);
    }
}