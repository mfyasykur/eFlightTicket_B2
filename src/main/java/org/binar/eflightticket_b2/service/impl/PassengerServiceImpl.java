package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.repository.PassengerRepository;
import org.binar.eflightticket_b2.service.PassengerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {


    private final PassengerRepository passengerRepository;
    private final Logger log =  LoggerFactory.getLogger(PassengerServiceImpl.class);
    private ModelMapper mapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, ModelMapper mapper) {
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
    }

    @Override
    public Passenger savePassenger(Passenger passenger) {
        Passenger save = passengerRepository.save(passenger);
        log.info("successfully persist data to database");
        return save;
    }

    @Override
    public List<Passenger> saveAllPassenger(List<Passenger> passengers) {
        List<Passenger> allPassengers = passengerRepository.saveAll(passengers);
        log.info("successfully persist all data to database");
        return allPassengers;
    }

    @Override
    public PassengerRequest mapToDTO(Passenger passenger) {
        return mapper.map(passenger, PassengerRequest.class);
    }

    @Override
    public Passenger mapToEntity(PassengerRequest passengerRequest) {
        return mapper.map(passengerRequest, Passenger.class);
    }



}
