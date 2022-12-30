package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.service.AirportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AirportServiceImplTest {

    @Mock private AirportRepository airportRepository;

    private AirportService airportService;

    @BeforeEach
    void setUp() {
        this.airportService = new AirportServiceImpl(airportRepository);
    }


    @Test
    @DisplayName("Create Airport Success")
    void addAirport() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");

        Mockito.when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        airportService.add(airport);
        Mockito.verify(airportRepository, times(1)).save(any(Airport.class));
    }


    public static List<Airport> getDummyData() {
        List<Airport> airportList = new ArrayList<>();
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");
        airportList.add(airport);
        return airportList;
    }
    @Test
    @DisplayName("Getting all airport Success")
    void findAllAirport() {
        List<Airport> airportList = getDummyData();
        given(airportRepository.findAll()).willReturn(airportList);
        airportService.findAll();
        Mockito.verify(airportRepository).findAll();

        var actualValue = airportService.findAll();
        Assertions.assertEquals(1L, actualValue.size());
        Assertions.assertEquals("Indonesia", actualValue.get(0).getAirportName());
        Assertions.assertEquals("IDN", actualValue.get(0).getAirportCode());
    }
    @Test
    @DisplayName("Getting airport by Id Success")
    void findAirportById(){
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");

        given(airportRepository.findById(airport.getId())).willReturn(Optional.of(airport));
        airportService.findById(airport.getId());

        Mockito.verify(airportRepository).findById(airport.getId());

        var actualValue = airportService.findById(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getAirportName());
        Assertions.assertEquals("IDN", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Getting airport by Code Success")
    void findAirportByCode() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");
        given(airportRepository.findByAirportCode(airport.getAirportCode())).willReturn(airport);

        airportService.findByAirportCode(airport.getAirportCode());
        Mockito.verify(airportRepository).findByAirportCode(airport.getAirportCode());

        var actualValue = airportService.findByAirportCode("IDN");
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getAirportName());
        Assertions.assertEquals("IDN", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Update airport Success")
    void update() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");
        airportRepository.save(airport);
        given(airportRepository.findById(airport.getId())).willReturn(Optional.of(airport));
        Mockito.verify(airportRepository).save(airport);

        var actualValue = airportService.update(1L, airport);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getAirportName());
        Assertions.assertEquals("IDN", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete airport Success")
    void deleteAirport() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");
        given(airportRepository.findById(1L)).willReturn(Optional.of(airport));
        airportRepository.delete(airport);
        Mockito.verify(airportRepository).delete(airport);

        var actualValue = airportService.delete(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getAirportName());
        Assertions.assertEquals("IDN", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Getting Resource not found")
    void resourceNotFound() {
        ResourceNotFoundException e = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> Mockito.verify(airportService.findById(2L)));
        Assertions.assertNotNull(e.getMessageMap());
    }

    @Test
    @DisplayName("Map to DTO Success")
    void mapToDtoTest() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Indonesia");
        airport.setAirportCode("IDN");

        AirportDTO airportDTO = airportService.mapToDto(airport);

        Assertions.assertEquals(airportDTO.getId(), airport.getId());
        Assertions.assertEquals(airportDTO.getAirportName(), airport.getAirportName());
        Assertions.assertEquals(airportDTO.getAirportCode(), airport.getAirportCode());
        Assertions.assertEquals(Airport.class, airport.getClass());
    }

    @Test
    @DisplayName("Map to Entity Success")
    void mapToEntityTest() {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setId(1L);
        airportDTO.setAirportName("Indonesia");
        airportDTO.setAirportCode("IDN");

        Airport airport = airportService.mapToEntity(airportDTO);

        Assertions.assertEquals(airport.getId(), airportDTO.getId());
        Assertions.assertEquals(airport.getAirportName(), airportDTO.getAirportName());
        Assertions.assertEquals(airport.getAirportCode(), airportDTO.getAirportCode());
        Assertions.assertEquals(AirportDTO.class, airportDTO.getClass());
    }
}
