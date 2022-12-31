package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.*;
import org.binar.eflightticket_b2.entity.*;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.FlightDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class FlightDetailServiceImplTest {

    @Mock
    FlightDetailRepository flightDetailRepository;

    @Mock
    AirportDetailRepository airportDetailRepository;

    @Mock
    AircraftRepository aircraftRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    FlightDetailServiceImpl flightDetailService;

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

    public static AirportDetail setDeparture() {

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(1L);
        airportDetail.setAirportDetails(setAirport());
        airportDetail.setCityDetails(setCity());
        airportDetail.setCountryDetails(setCountry());

        return airportDetail;
    }

    public static AirportDetail setArrival() {

        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setId(2L);
        airportDetail.setAirportDetails(setAirport());
        airportDetail.setCityDetails(setCity());
        airportDetail.setCountryDetails(setCountry());

        return airportDetail;
    }

    public static Aircraft setAircraft() {

        Aircraft aircraft = new Aircraft();
        aircraft.setId(1L);
        aircraft.setManufacture("Boeing");
        aircraft.setManufactureCode("Boeing-99");
        aircraft.setRegisterCode("AN-69");
        aircraft.setSeatCapacity(999);
        aircraft.setBaggageCapacity(77);
        aircraft.setSizeType(Aircraft.SizeType.LARGE);

        return aircraft;
    }

    @Test
    @DisplayName("Add FlightDetail Success")
    void addFlightDetailSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                        .id(1L)
                        .departure(departure)
                        .arrival(arrival)
                        .aircraftDetail(aircraft)
                        .build();

        when(airportDetailRepository.findById(1L)).thenReturn(Optional.of(departure));
        when(airportDetailRepository.findById(2L)).thenReturn(Optional.of(arrival));
        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));

        when(flightDetailRepository.save(any(FlightDetail.class))).thenReturn(flightDetailRequest);

        FlightDetail flightDetailResponse = flightDetailService.addFlightDetail(flightDetailRequest.getDeparture().getId(), flightDetailRequest.getArrival().getId(), flightDetailRequest.getAircraftDetail().getId());

        flightDetailRepository.save(flightDetailResponse);

        when(flightDetailRequest.getDeparture()).thenReturn(flightDetailResponse.getDeparture());

        verify(aircraftRepository).findById(flightDetailRequest.getAircraftDetail().getId());
    }

    @Test
    @DisplayName("Add FlightDetail FAILED DEPARTURE ID NOT FOUND")
    void addFlightDetailDepartureIdNotFound() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                .id(1L)
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        when(airportDetailRepository.findById(flightDetailRequest.getDeparture().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightDetailService.addFlightDetail(1L, 2L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(flightDetailRequest.getDeparture().getId());
    }

    @Test
    @DisplayName("Add FlightDetail FAILED ARRIVAL ID NOT FOUND")
    void addFlightDetailArrivalIdNotFound() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                .id(1L)
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        when(airportDetailRepository.findById(flightDetailRequest.getDeparture().getId())).thenReturn(Optional.of(departure));
        when(airportDetailRepository.findById(flightDetailRequest.getArrival().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightDetailService.addFlightDetail(1L, 2L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(flightDetailRequest.getDeparture().getId());
        verify(airportDetailRepository).findById(flightDetailRequest.getArrival().getId());
    }

    @Test
    @DisplayName("Add FlightDetail FAILED AIRCRAFT ID NOT FOUND")
    void addFlightDetailAircraftIdNotFound() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                .id(1L)
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        when(airportDetailRepository.findById(flightDetailRequest.getDeparture().getId())).thenReturn(Optional.of(departure));
        when(airportDetailRepository.findById(flightDetailRequest.getArrival().getId())).thenReturn(Optional.of(arrival));
        when(aircraftRepository.findById(flightDetailRequest.getAircraftDetail().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightDetailService.addFlightDetail(1L, 2L, 1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(flightDetailRequest.getDeparture().getId());
        verify(airportDetailRepository).findById(flightDetailRequest.getArrival().getId());
        verify(aircraftRepository).findById(flightDetailRequest.getAircraftDetail().getId());
    }

    @Test
    @DisplayName("Delete FlightDetail By Id SUCCESS")
    void deleteFlightDetailSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                .id(1L)
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        when(flightDetailRepository.findById(anyLong())).thenReturn(Optional.of(flightDetailRequest));

        FlightDetail deletedFlightDetail = flightDetailService.deleteFlightDetail(anyLong());

        assertEquals(flightDetailRequest.getId(), deletedFlightDetail.getId());

        verify(flightDetailRepository).delete(flightDetailRequest);
    }

    @Test
    @DisplayName("Delete FlightDetail By Id FAILED ID NOT FOUND")
    void deleteFlightDetailByIdNotFound() {

        when(flightDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightDetailService.deleteFlightDetail(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(flightDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get All FlightDetail SUCCESS")
    void getAllFlightDetailSuccess() {

        List<FlightDetail> flightDetailList = List.of(new FlightDetail(), new FlightDetail());

        when(flightDetailRepository.findAll()).thenReturn(flightDetailList);

        flightDetailService.getAllFlightDetails();

        verify(flightDetailRepository).findAll();
    }

    @Test
    @DisplayName("Get FlightDetail By Id SUCCESS")
    void getFlightDetailByIdSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        Aircraft aircraft = setAircraft();
        FlightDetail flightDetailRequest = FlightDetail.builder()
                .id(1L)
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        when(flightDetailRepository.findById(anyLong())).thenReturn(Optional.of(flightDetailRequest));

        FlightDetail retrievedFlightDetail = flightDetailService.getFlightDetailById(anyLong());

        assertEquals(flightDetailRequest.getId(), retrievedFlightDetail.getId());

        verify(flightDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get FlightDetail by Id FAILED ID NOT FOUND")
    void getFlightDetailByIdNotFound() {

        when(flightDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightDetailService.getFlightDetailById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(flightDetailRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Map Entity to DTO SUCCESS")
    void mapToDTOSuccess() {

        FlightDetailDTO flightDetailDTO = new FlightDetailDTO();

        FlightDetail flightDetail = new FlightDetail();

        when(flightDetailService.mapToDto(flightDetail)).thenReturn(flightDetailDTO);

        FlightDetailDTO dtoResult = flightDetailService.mapToDto(flightDetail);

        assertEquals(flightDetailDTO.getDeparture(), dtoResult.getDeparture());
    }

    @Test
    @DisplayName("Map DTO to Entity SUCCESS")
    void mapToEntitySuccess() {

        FlightDetail flightDetail = new FlightDetail();
        FlightDetailDTO flightDetailDTO = new FlightDetailDTO();

        when(flightDetailService.mapToEntity(flightDetailDTO)).thenReturn(flightDetail);

        FlightDetail entityResult = flightDetailService.mapToEntity(flightDetailDTO);

        assertEquals(entityResult.getDeparture(), flightDetail.getDeparture());
    }
}
