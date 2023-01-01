package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDetailDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.entity.AirportDetail;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AirportDetailServiceImplTest {

    @Mock
    AirportDetailRepository airportDetailRepository;

    @Mock
    AirportRepository airportRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    CountryRepository countryRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    AirportDetailServiceImpl airportDetailService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    public static Airport setAirport() {

        Airport airport = new Airport();
        airport.setId(1L);
        airport.setAirportName("Soekarno-Hatta");
        airport.setAirportCode("CGK");

        return airport;
    }

    public static City setCity() {

        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        return city;
    }

    public static Country setCountry() {

        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("ID");

        return country;
    }

    @Test
    @DisplayName("Add Airport Detail SUCCESS")
    void addAirportDetailSuccess() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(airportDetailRepository.findById(1L)).thenReturn(Optional.of(airportDetail));

        when(airportDetailRepository.save(any(AirportDetail.class))).thenReturn(airportDetail);

        AirportDetail airportDetailResponse = airportDetailService.add(airportDetail.getCountryDetails().getId(), airportDetail.getCityDetails().getId(), airportDetail.getAirportDetails().getId());

        when(airportDetail.getAirportDetails()).thenReturn(airportDetailResponse.getAirportDetails());

        assertEquals(airportDetail.getAirportDetails(), airportDetailResponse.getAirportDetails());
    }

    @Test
    @DisplayName("Add AirportDetail FAILED AIRPORT ID NOT FOUND")
    void addAirportDetailAirportIdNotFound() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportRepository.findById(airportDetail.getAirportDetails().getId())).thenReturn(Optional.empty());
        when(cityRepository.findById(airportDetail.getCityDetails().getId())).thenReturn(Optional.of(city));
        when(countryRepository.findById(airportDetail.getCountryDetails().getId())).thenReturn(Optional.of(country));

        assertThatThrownBy(() -> airportDetailService.add(1L, 1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportRepository).findById(airportDetail.getAirportDetails().getId());
    }

    @Test
    @DisplayName("Add AirportDetail FAILED CITY ID NOT FOUND")
    void addAirportDetailCityIdNotFound() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportRepository.findById(airportDetail.getAirportDetails().getId())).thenReturn(Optional.of(airport));
        when(cityRepository.findById(airportDetail.getCityDetails().getId())).thenReturn(Optional.empty());
        when(countryRepository.findById(airportDetail.getCountryDetails().getId())).thenReturn(Optional.of(country));

        assertThatThrownBy(() -> airportDetailService.add(1L, 1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportRepository).findById(airportDetail.getAirportDetails().getId());
        verify(cityRepository).findById(airportDetail.getCityDetails().getId());
    }

    @Test
    @DisplayName("Add AirportDetail FAILED COUNTRY ID NOT FOUND")
    void addAirportDetailCountryIdNotFound() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportRepository.findById(airportDetail.getAirportDetails().getId())).thenReturn(Optional.of(airport));
        when(cityRepository.findById(airportDetail.getCityDetails().getId())).thenReturn(Optional.of(city));
        when(countryRepository.findById(airportDetail.getCountryDetails().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> airportDetailService.add(1L, 1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportRepository).findById(airportDetail.getAirportDetails().getId());
        verify(cityRepository).findById(airportDetail.getCityDetails().getId());
        verify(countryRepository).findById(airportDetail.getCountryDetails().getId());
    }

    @Test
    @DisplayName("Delete AirportDetail By Id SUCCESS")
    void deleteAirportDetailSuccess() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportDetailRepository.findById(anyLong())).thenReturn(Optional.of(airportDetail));

        AirportDetail deletedAirportDetail = airportDetailService.delete(anyLong());

        assertEquals(airportDetail.getId(), deletedAirportDetail.getId());

        verify(airportDetailRepository).delete(airportDetail);
    }

    @Test
    @DisplayName("Delete AirportDetail By Id FAILED ID NOT FOUND")
    void deleteAirportDetailByIdNotFound() {

        when(airportDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> airportDetailService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get All AirportDetail SUCCESS")
    void getAllAirportDetailSuccess() {

        List<AirportDetail> airportDetailList = List.of(new AirportDetail(), new AirportDetail());

        when(airportDetailRepository.findAll()).thenReturn(airportDetailList);

        airportDetailService.findAll();

        verify(airportDetailRepository).findAll();
    }

    @Test
    @DisplayName("Get AirportDetail By Id SUCCESS")
    void getAirportDetailByIdSuccess() {

        Airport airport = setAirport();
        City city = setCity();
        Country country = setCountry();

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        when(airportDetailRepository.findById(anyLong())).thenReturn(Optional.of(airportDetail));

        AirportDetail retrievedAirportDetail = airportDetailService.findById(anyLong());

        assertEquals(airportDetail.getId(), retrievedAirportDetail.getId());

        verify(airportDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get AirportDetail by Id FAILED ID NOT FOUND")
    void getAirportDetailByIdNotFound() {

        when(airportDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> airportDetailService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Map Entity to DTO SUCCESS")
    void mapToDTOSuccess() {

        AirportDetailDTO airportDetailDTO = new AirportDetailDTO();

        AirportDetail airportDetail = new AirportDetail();

        when(airportDetailService.mapToDto(airportDetail)).thenReturn(airportDetailDTO);

        AirportDetailDTO dtoResult = airportDetailService.mapToDto(airportDetail);

        assertEquals(airportDetailDTO.getCityDetails(), dtoResult.getCityDetails());
    }

    @Test
    @DisplayName("Map DTO to Entity SUCCESS")
    void mapToEntitySuccess() {

        AirportDetail airportDetail = new AirportDetail();
        AirportDetailDTO airportDetailDTO = new AirportDetailDTO();

        when(airportDetailService.mapToEntity(airportDetailDTO)).thenReturn(airportDetail);

        AirportDetail entityResult = airportDetailService.mapToEntity(airportDetailDTO);

        assertEquals(entityResult.getAirportDetails(), airportDetail.getAirportDetails());
    }

}
