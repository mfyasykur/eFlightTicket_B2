package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AirportServiceImplTest {

    @Mock
    AirportRepository airportRepository;

    private AirportServiceImpl airportService;

    @BeforeEach
    void setUp() {
        this.airportService = new AirportServiceImpl(airportRepository);
    }


    @Test
    @DisplayName("Create Airport Success")
    void addAirport() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        Mockito.when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        airportService.add(airport);
        Mockito.verify(airportRepository, times(1)).save(any(Airport.class));
    }


    public static List<Airport> getDummyData() {
        List<Airport> airportList = new ArrayList<>();
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
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
        Assertions.assertEquals("Soekarno - Hatta", actualValue.get(0).getAirportName());
        Assertions.assertEquals("SKH", actualValue.get(0).getAirportCode());
    }
    @Test
    @DisplayName("Getting airport by Id Success")
    void findAirportById(){
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        given(airportRepository.findById(airport.getId())).willReturn(Optional.of(airport));
        airportService.findById(airport.getId());

        Mockito.verify(airportRepository).findById(airport.getId());

        var actualValue = airportService.findById(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Getting airport by Code Success")
    void findAirportByCode() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
        given(airportRepository.findByAirportCode(airport.getAirportCode())).willReturn(Optional.of(airport));

        airportService.findByAirportCode(airport.getAirportCode());
        Mockito.verify(airportRepository).findByAirportCode(airport.getAirportCode());

        var actualValue = airportService.findByAirportCode("SKH");
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Find airport by Code Not Found")
    void findAirportByCodeNotFound(){
        when(airportRepository.findByAirportCode(anyString())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> airportService.findByAirportCode("airportCode"))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(airportRepository, times(1)).findByAirportCode(anyString());
    }

    @Test
    @DisplayName("Update airport Success")
    void updateAirportSuccess() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
        airportRepository.save(airport);
        given(airportRepository.findById(airport.getId())).willReturn(Optional.of(airport));
        Mockito.verify(airportRepository).save(airport);

        var actualValue = airportService.update(1L, airport);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Update airport Not Found")
    void updateAirportNotFound(){
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

        when(airportRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> airportService.update(11L, airport))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(airportRepository, times(1)).findById(11L);
    }

    @Test
    @DisplayName("Delete airport Success")
    void deleteAirportSuccess() throws ResourceNotFoundException{
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");
        given(airportRepository.findById(1L)).willReturn(Optional.of(airport));
        airportRepository.delete(airport);
        Mockito.verify(airportRepository).delete(airport);

        var actualValue = airportService.delete(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Soekarno - Hatta", actualValue.getAirportName());
        Assertions.assertEquals("SKH", actualValue.getAirportCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete airport Not Found")
    void deleteAirportNotFound(){
        when(airportRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> airportService.delete(11L))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(airportRepository, times(1)).findById(11L);
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
        airport.setAirportName("Soekarno - Hatta");
        airport.setAirportCode("SKH");

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
        airportDTO.setAirportName("Soekarno - Hatta");
        airportDTO.setAirportCode("SKH");

        Airport airport = airportService.mapToEntity(airportDTO);

        Assertions.assertEquals(airport.getId(), airportDTO.getId());
        Assertions.assertEquals(airport.getAirportName(), airportDTO.getAirportName());
        Assertions.assertEquals(airport.getAirportCode(), airportDTO.getAirportCode());
        Assertions.assertEquals(AirportDTO.class, airportDTO.getClass());
    }
}
