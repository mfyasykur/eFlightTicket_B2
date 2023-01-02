package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.dto.RouteRequest;
import org.binar.eflightticket_b2.entity.*;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.RouteRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RouteServiceImplTest {

    @Mock
    RouteRepository routeRepository;

    @Mock
    AirportDetailRepository airportDetailRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    RouteServiceImpl routeService;

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

    @Test
    @DisplayName("Add Route SUCCESS")
    void addRouteSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureId(1L);
        routeRequest.setArrivalId(2L);
        routeRequest.setDuration(110);
        routeRequest.setBasePrice(900_000);

        Route route = new Route();
        route.setId(1L);
        route.setDeparture(departure);
        route.setArrival(arrival);
        route.setDuration(routeRequest.getDuration());
        route.setBasePrice(routeRequest.getBasePrice());

        when(airportDetailRepository.findById(1L)).thenReturn(Optional.of(departure));
        when(airportDetailRepository.findById(2L)).thenReturn(Optional.of(arrival));

        when(routeRepository.save(any(Route.class))).thenReturn(route);

        Route routeResponse = routeService.addRoute(routeRequest);

        routeRepository.save(routeResponse);

        when(route.getBasePrice()).thenReturn(routeResponse.getBasePrice());

        verify(airportDetailRepository).findById(route.getDeparture().getId());
    }

    @Test
    @DisplayName("Add Route FAILED DEPARTURE ID NOT FOUND")
    void addRouteDepartureIdNotFound() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureId(1L);
        routeRequest.setArrivalId(2L);
        routeRequest.setDuration(110);
        routeRequest.setBasePrice(900_000);

        Route route = new Route();
        route.setId(1L);
        route.setDeparture(departure);
        route.setArrival(arrival);
        route.setDuration(routeRequest.getDuration());
        route.setBasePrice(routeRequest.getBasePrice());

        when(airportDetailRepository.findById(route.getDeparture().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.addRoute(routeRequest))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(route.getDeparture().getId());
    }

    @Test
    @DisplayName("Add Route FAILED ARRIVAL ID NOT FOUND")
    void addRouteArrivalIdNotFound() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureId(1L);
        routeRequest.setArrivalId(2L);
        routeRequest.setDuration(110);
        routeRequest.setBasePrice(900_000);

        Route route = new Route();
        route.setId(1L);
        route.setDeparture(departure);
        route.setArrival(arrival);
        route.setDuration(routeRequest.getDuration());
        route.setBasePrice(routeRequest.getBasePrice());

        when(airportDetailRepository.findById(route.getDeparture().getId())).thenReturn(Optional.of(departure));
        when(airportDetailRepository.findById(route.getArrival().getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.addRoute(routeRequest))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(airportDetailRepository).findById(route.getDeparture().getId());
        verify(airportDetailRepository).findById(route.getArrival().getId());
    }

    @Test
    @DisplayName("Delete Route By Id SUCCESS")
    void deleteRouteSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureId(1L);
        routeRequest.setArrivalId(2L);
        routeRequest.setDuration(110);
        routeRequest.setBasePrice(900_000);

        Route route = new Route();
        route.setId(1L);
        route.setDeparture(departure);
        route.setArrival(arrival);
        route.setDuration(routeRequest.getDuration());
        route.setBasePrice(routeRequest.getBasePrice());

        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route));

        Route deletedRoute = routeService.deleteRoute(anyLong());

        assertEquals(route.getId(), deletedRoute.getId());

        verify(routeRepository).delete(route);
    }

    @Test
    @DisplayName("Delete Route By Id FAILED ID NOT FOUND")
    void deleteRouteByIdNotFound() {

        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.deleteRoute(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(routeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get All Route SUCCESS")
    void getAllRouteSuccess() {

        List<Route> routeList = List.of(new Route(), new Route());

        when(routeRepository.findAll()).thenReturn(routeList);

        routeService.getAllRoutes();

        verify(routeRepository).findAll();
    }

    @Test
    @DisplayName("Get Route By Id SUCCESS")
    void getRouteByIdSuccess() {

        AirportDetail departure = setDeparture();
        AirportDetail arrival = setArrival();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureId(1L);
        routeRequest.setArrivalId(2L);
        routeRequest.setDuration(110);
        routeRequest.setBasePrice(900_000);

        Route route = new Route();
        route.setId(1L);
        route.setDeparture(departure);
        route.setArrival(arrival);
        route.setDuration(routeRequest.getDuration());
        route.setBasePrice(routeRequest.getBasePrice());

        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route));

        Route retrievedRoute = routeService.getRouteById(anyLong());

        assertEquals(route.getId(), retrievedRoute.getId());

        verify(routeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get Route by Id FAILED ID NOT FOUND")
    void getRouteByIdNotFound() {

        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.getRouteById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(routeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Map Entity to DTO SUCCESS")
    void mapToDTOSuccess() {

        RouteDTO routeDTO = new RouteDTO();

        Route route = new Route();

        when(routeService.mapToDto(route)).thenReturn(routeDTO);

        RouteDTO dtoResult = routeService.mapToDto(route);

        assertEquals(routeDTO.getDeparture(), dtoResult.getDeparture());
    }

    @Test
    @DisplayName("Map DTO to Entity SUCCESS")
    void mapToEntitySuccess() {

        Route route = new Route();
        RouteDTO routeDTO = new RouteDTO();

        when(routeService.mapToEntity(routeDTO)).thenReturn(route);

        Route entityResult = routeService.mapToEntity(routeDTO);

        assertEquals(entityResult.getDeparture(), route.getDeparture());
    }
}
