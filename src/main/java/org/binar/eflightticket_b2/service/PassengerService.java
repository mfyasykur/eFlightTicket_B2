package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.entity.Passenger;

import java.util.List;

public interface PassengerService {

    Passenger savePassenger(Passenger passenger);
    List<Passenger> saveAllPassenger(List<Passenger> passengers);


    PassengerRequest mapToDTO(Passenger passenger);
    Passenger mapToEntity(PassengerRequest passengerRequest);

}
