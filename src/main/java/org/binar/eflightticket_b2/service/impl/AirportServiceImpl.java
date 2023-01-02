package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.service.AirportService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AirportServiceImpl implements AirportService {

    private static final String ENTITY = "airport";
    private static final String ERROR = "Error Not Found: airport with ID {}";
    private final Logger log =  LoggerFactory.getLogger(AirportServiceImpl.class);

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    private final AirportRepository airportRepository;

    @Override
    public Airport add(Airport airport) {
        log.info("Has successfully created airport data!");
        return airportRepository.save(airport);
    }

    @Override
    public Airport update(Long id, Airport airport) {
        Airport result = airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info(ERROR, id);
                    exception.setApiResponse();
                    throw exception;
                });

        result.setAirportName(airport.getAirportName());
        result.setAirportCode(airport.getAirportCode());
        airportRepository.save(result);
        log.info("Has successfully updated airport data!");
        return result;
    }

    @Override
    public Airport delete(Long id) {
        Airport result = airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info(ERROR, id);
                    exception.setApiResponse();
                    throw exception;
                });
        airportRepository.delete(result);
        log.info("Has successfully deleted airport data!");
        return result;
    }

    @Override
    public List<Airport> findAll() {
        log.info("Has successfully found all airport data!");
        return airportRepository.findAll();
    }

    @Override
    public Airport findById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info(ERROR, id);
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found airport data from id {}", id);
        return airport;
    }

    @Override
    public Airport findByAirportCode(String airportCode) {
        Airport byAirportCode = airportRepository.findByAirportCode(airportCode)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "airportCode", airportCode);
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found airport data from code {}", airportCode);
        return byAirportCode;
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