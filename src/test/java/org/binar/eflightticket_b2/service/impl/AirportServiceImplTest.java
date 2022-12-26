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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static List<Airport> getDummyData() {
        List<Airport> airportList = new ArrayList<>();
        Airport airport = new Airport();
        airport.setId(Long.valueOf("1"));
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
        airportList.add(airport);
        return airportList;
    }

    @Test
    @DisplayName("Getting all airport Success")
    void getAllAirport() {
        List<Airport> airportList = getDummyData();
        Mockito.when(airportRepository.findAll()).thenReturn(airportList);

        var actualValue = airportServiceImpl.findAll();
        Assertions.assertEquals(1, actualValue.size());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.get(0).getAirportName());
        Assertions.assertEquals("SKH", actualValue.get(0).getAirportCode());
    }

    @Test
    @DisplayName("Update airport success")
    void updateStudio() {
        Airport airport = new Airport();
        airport.setId(Long.valueOf("1"));
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);

        var actualValue = airportServiceImpl.update(1L, airport);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
    }

    @Test
    @DisplayName("Getting airport By Id")
    void airportFindById() {
        Airport airport = new Airport();
        airport.setId(Long.valueOf("1"));
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        Mockito.when(airportRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(airport));

        var actualValue = airportServiceImpl.update(1L, airport);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete airport success")
    void deleteAirport(){
        Airport airport = new Airport();
        airport.setId(Long.valueOf("1"));
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        airportRepository.delete(airport);
    }
}
