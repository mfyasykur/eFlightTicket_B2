package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.binar.eflightticket_b2.service.AircraftService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private static final String ENTITY = "aircraft";

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public Aircraft addAircraft(Aircraft aircraft) {

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
        aircraftRepository.delete(aircraft);

        return aircraft;
    }

    @Override
    public List<Aircraft> getAllAircraft() {

        return aircraftRepository.findAll();
    }

    @Override
    public Aircraft getAircraftById(Long id) {

        return aircraftRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
    }

    //DTO Converter
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
