package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.binar.eflightticket_b2.service.AircraftService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private final Logger log = LoggerFactory.getLogger(AircraftServiceImpl.class);

    private static final String ENTITY = "aircraft";

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public Aircraft addAircraft(Aircraft aircraft) {

        log.info("Has successfully created aircraft data with ID : {}", aircraft.getId());

        return aircraftRepository.save(aircraft);
    }

    @Override
    public Aircraft updateAircraft(Long id, Aircraft aircraft) {

        Aircraft result = aircraftRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setManufacture(aircraft.getManufacture());
        result.setManufactureCode(aircraft.getManufactureCode());
        result.setRegisterCode(aircraft.getRegisterCode());
        result.setSeatCapacity(aircraft.getSeatCapacity());
        result.setBaggageCapacity(aircraft.getBaggageCapacity());
        result.setSizeType(aircraft.getSizeType());
        aircraftRepository.save(result);

        log.info("Has successfully updated aircraft data with ID : {}", result.getId());

        return result;
    }

    @Override
    public Aircraft deleteAircraft(Long id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully deleted aircraft data with ID : {}", aircraft.getId());

        aircraftRepository.delete(aircraft);

        return aircraft;
    }

    @Override
    public List<Aircraft> getAllAircraft() {

        log.info("Has successfully retrieved all aircraft data");

        return aircraftRepository.findAll();
    }

    @Override
    public Aircraft getAircraftById(Long id) {

        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully retrieved aircraft data with ID : {}", aircraft.getId());

        return aircraft;
    }

    //DTO Mapper
    ModelMapper mapper = new ModelMapper();

    @Override
    public AircraftDTO mapToDto(Aircraft aircraft) {
        return mapper.map(aircraft, AircraftDTO.class);
    }

    @Override
    public Aircraft mapToEntity(AircraftDTO aircraftDTO) {
        return mapper.map(aircraftDTO, Aircraft.class);
    }
}
