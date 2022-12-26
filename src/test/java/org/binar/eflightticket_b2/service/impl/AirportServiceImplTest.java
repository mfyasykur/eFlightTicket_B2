package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AirportServiceImplTest {
    @InjectMocks
    AirportServiceImpl airportServiceImpl;

    @Mock
    AirportRepository airportRepository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Airport Success")
    void addAirport() {
        Airport airport = new Airport();
        airport.setId(Long.valueOf("1"));
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
        Assertions.assertEquals(airport, airportServiceImpl.add(airport));
    }

}
