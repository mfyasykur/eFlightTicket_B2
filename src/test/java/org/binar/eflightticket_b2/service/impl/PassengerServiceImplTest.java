package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.BAGGAGE;
import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.entity.AGE;
import org.binar.eflightticket_b2.entity.GENDER;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.repository.PassengerRepository;
import org.binar.eflightticket_b2.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @Mock
    PassengerRepository passengerRepository;
    @Mock
    ModelMapper modelMapper;

    @Autowired
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
       passengerService = new PassengerServiceImpl(passengerRepository, modelMapper);
    }

    @Test
    void savePassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1l);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        System.out.println(passenger);
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);
        Passenger savePassenger = passengerService.savePassenger(passenger);
        Mockito.verify(passengerRepository, Mockito.times(1)).save(any(Passenger.class));
        assertThat(savePassenger).usingRecursiveComparison().isEqualTo(passenger);

    }

    @Test
    void saveAllPassenger() {
        Passenger passenger = new Passenger();
        passenger.setId(1l);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        Passenger passenger2 = new Passenger();
        passenger.setId(2l);
        passenger.setFirstName("Annisa");
        passenger.setLastName("Moreska");
        passenger.setGender(GENDER.MRS);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        List<Passenger> listOfPassenger = List.of(passenger, passenger2);
        when(passengerRepository.saveAll(any(List.class))).thenReturn(listOfPassenger);
        List<Passenger> passengersList = passengerService.saveAllPassenger(listOfPassenger);
        assertThat(passengersList.get(0).getId()).usingRecursiveComparison().isEqualTo(listOfPassenger.get(0).getId());
        verify(passengerRepository, times(1)).saveAll(listOfPassenger);
    }

    @Test
    void mapToDTO() {
        Passenger passenger = new Passenger();
        passenger.setId(1l);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        PassengerRequest passengerDTO = PassengerRequest.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .build();
        when(passengerService.mapToDTO(passenger)).thenReturn(passengerDTO);
        PassengerRequest passengerRequest = passengerService.mapToDTO(passenger);
        assertThat(passengerRequest).usingRecursiveComparison().isEqualTo(passengerDTO);
    }

    @Test
    void mapToEntity() {
        Passenger passenger = new Passenger();
        passenger.setId(1l);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        PassengerRequest passengerDTO = PassengerRequest.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .build();
        when(passengerService.mapToEntity(passengerDTO)).thenReturn(passenger);
        Passenger savedPassenger = passengerService.mapToEntity(passengerDTO);
        assertThat(savedPassenger).usingRecursiveComparison().isEqualTo(passenger);
    }
}