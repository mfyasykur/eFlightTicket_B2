package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.FlightDetail;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.FlightDetailRepository;
import org.binar.eflightticket_b2.repository.RouteRepository;
import org.binar.eflightticket_b2.repository.ScheduleRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ScheduleServiceImplTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    RouteRepository routeRepository;

    @Mock
    FlightDetailRepository flightDetailRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ScheduleServiceImpl scheduleService;

    static ScheduleRequest setScheduleRequest() {

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setDepartureDate(LocalDate.now());
        scheduleRequest.setArrivalDate(LocalDate.now());
        scheduleRequest.setDepartureTime(LocalTime.parse("09:00:00"));
        scheduleRequest.setArrivalTime(LocalTime.parse("11:00:00"));
        scheduleRequest.setRouteId(1L);
        scheduleRequest.setFlightClass(Schedule.FlightClass.BUSINESS);
        scheduleRequest.setFlightDetailId(1L);

        return scheduleRequest;
    }

    @Test
    @DisplayName("Add Schedule SUCCESS")
    void addScheduleSuccess() {

        Route route = new Route();
        route.setId(1L);
        route.setBasePrice(1000_000);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setId(1L);

        ScheduleRequest scheduleRequest = setScheduleRequest();

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setRoute(route);
        schedule.setNetPrice(1300_000);
        schedule.setFlightClass(Schedule.FlightClass.BUSINESS);
        schedule.setFlightDetail(flightDetail);

        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));
        when(flightDetailRepository.findById(1L)).thenReturn(Optional.of(flightDetail));

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        Schedule scheduleResponse = scheduleService.addSchedule(scheduleRequest);

        scheduleRepository.save(scheduleResponse);

        when(schedule.getDepartureTime()).thenReturn(scheduleResponse.getDepartureTime());

        verify(routeRepository).findById(schedule.getRoute().getId());
        verify(flightDetailRepository).findById(schedule.getFlightDetail().getId());
    }

    @Test
    @DisplayName("Add Schedule FAILED ROUTE ID NOT FOUND")
    void addScheduleRouteIdNotFound() {

        Route route = new Route();
        route.setId(1L);
        route.setBasePrice(1000_000);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setId(1L);

        ScheduleRequest scheduleRequest = setScheduleRequest();

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setRoute(route);
        schedule.setNetPrice(1300_000);
        schedule.setFlightClass(Schedule.FlightClass.BUSINESS);
        schedule.setFlightDetail(flightDetail);

        when(routeRepository.findById(route.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.addSchedule(scheduleRequest))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(routeRepository).findById(route.getId());
    }

    @Test
    @DisplayName("Add Schedule FAILED FLIGHTDETAIL ID NOT FOUND")
    void addScheduleFlightDetailIdNotFound() {

        Route route = new Route();
        route.setId(1L);
        route.setBasePrice(1000_000);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setId(1L);

        ScheduleRequest scheduleRequest = setScheduleRequest();

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setRoute(route);
        schedule.setNetPrice(1300_000);
        schedule.setFlightClass(Schedule.FlightClass.BUSINESS);
        schedule.setFlightDetail(flightDetail);

        when(routeRepository.findById(route.getId())).thenReturn(Optional.of(route));
        when(flightDetailRepository.findById(flightDetail.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.addSchedule(scheduleRequest))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(routeRepository).findById(route.getId());
        verify(flightDetailRepository).findById(flightDetail.getId());
    }

    @Test
    @DisplayName("Delete Schedule By Id SUCCESS")
    void deleteRouteSuccess() {

        Route route = new Route();
        route.setId(1L);
        route.setBasePrice(1000_000);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setId(1L);

        ScheduleRequest scheduleRequest = setScheduleRequest();

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setRoute(route);
        schedule.setNetPrice(1300_000);
        schedule.setFlightClass(Schedule.FlightClass.BUSINESS);
        schedule.setFlightDetail(flightDetail);

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        Schedule deletedSchedule = scheduleService.deleteSchedule(anyLong());

        assertEquals(schedule.getId(), deletedSchedule.getId());

        verify(scheduleRepository).delete(schedule);
    }

    @Test
    @DisplayName("Delete Schedule By Id FAILED ID NOT FOUND")
    void deleteScheduleByIdNotFound() {

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.deleteSchedule(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(scheduleRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get All Schedule SUCCESS")
    void getAllScheduleSuccess() {

        List<Schedule> scheduleList = List.of(new Schedule(), new Schedule());

        when(scheduleRepository.findAll()).thenReturn(scheduleList);

        scheduleService.getAllSchedules();

        verify(scheduleRepository).findAll();
    }

//    @Test
//    @DisplayName("Get All Schedules By Default Filter SUCCESS")
//    void getAllSchedulesByDefaultFilterSuccess() {
//
//        List<Schedule> scheduleList = List.of(new Schedule(), new Schedule());
//        Page<Schedule> schedulePage= Mockito.mock(Page.class);
//
//        String departureCityName = "Jakarta";
//        String arrivalCityName = "Denpasar";
//        LocalDate departureDate = LocalDate.parse("2022-12-31");
//        Schedule.FlightClass flightClass = Schedule.FlightClass.valueOf("ECONOMY");
//        String[] sort = {"departureTime", "asc"};
//        Pageable paging = PageRequest.of(0, 2, Sort.by(sort));
//
//        when(scheduleRepository.findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClassOrderByNetPrice(departureCityName, arrivalCityName, departureDate, flightClass, paging)).thenReturn(schedulePage);
//
//        scheduleService.getAllSchedulesByDefaultFilter(departureCityName, arrivalCityName, departureDate, flightClass.toString(), 0, 2, sort);
//
//        verify(scheduleRepository).findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClassOrderByNetPrice(departureCityName, arrivalCityName, departureDate, flightClass, paging);
//    }

    @Test
    @DisplayName("Get Schedule By Id SUCCESS")
    void getScheduleByIdSuccess() {

        Route route = new Route();
        route.setId(1L);
        route.setBasePrice(1000_000);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setId(1L);

        ScheduleRequest scheduleRequest = setScheduleRequest();

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDepartureDate(scheduleRequest.getDepartureDate());
        schedule.setArrivalDate(scheduleRequest.getArrivalDate());
        schedule.setDepartureTime(scheduleRequest.getDepartureTime());
        schedule.setArrivalTime(scheduleRequest.getArrivalTime());
        schedule.setRoute(route);
        schedule.setNetPrice(1300_000);
        schedule.setFlightClass(Schedule.FlightClass.BUSINESS);
        schedule.setFlightDetail(flightDetail);

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        Schedule retrievedSchedule = scheduleService.getScheduleById(anyLong());

        assertEquals(schedule.getId(), retrievedSchedule.getId());

        verify(scheduleRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Get Schedule by Id FAILED ID NOT FOUND")
    void getScheduleByIdNotFound() {

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.getScheduleById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(scheduleRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Map Entity to DTO SUCCESS")
    void mapToDTOSuccess() {

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        Schedule schedule = new Schedule();

        when(scheduleService.mapToDto(schedule)).thenReturn(scheduleDTO);

        ScheduleDTO dtoResult = scheduleService.mapToDto(schedule);

        assertEquals(scheduleDTO.getDepartureTime(), dtoResult.getDepartureTime());
    }

    @Test
    @DisplayName("Map DTO to Entity SUCCESS")
    void mapToEntitySuccess() {

        Schedule schedule = new Schedule();
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        when(scheduleService.mapToEntity(scheduleDTO)).thenReturn(schedule);

        Schedule entityResult = scheduleService.mapToEntity(scheduleDTO);

        assertEquals(entityResult.getDepartureTime(), schedule.getDepartureTime());
    }

}
