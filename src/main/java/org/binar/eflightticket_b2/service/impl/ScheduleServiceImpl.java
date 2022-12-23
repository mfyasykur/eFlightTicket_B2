package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.*;
import org.binar.eflightticket_b2.entity.FlightDetail;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.FlightDetailRepository;
import org.binar.eflightticket_b2.repository.RouteRepository;
import org.binar.eflightticket_b2.repository.ScheduleRepository;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private static final String ENTITY = "schedule";

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FlightDetailRepository flightDetailRepository;

    private Sort.Direction getSortDirection(String direction) {

        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @Override
    public Schedule addSchedule(ScheduleRequest scheduleRequest) {

        Route route = routeRepository.findById(scheduleRequest.getRouteId())
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("route", "id", scheduleRequest.getRouteId());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Route found with ID : {}", route.getId());

        FlightDetail flightDetail = flightDetailRepository.findById(scheduleRequest.getFlightDetailId())
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("flightDetail", "id", scheduleRequest.getFlightDetailId());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("FlightDetail found with ID : {}", flightDetail.getId());

        Integer netPrice = route.getBasePrice();
        if (scheduleRequest.getFlightClass().equals(Schedule.FlightClass.ECONOMY)) {
            netPrice += (int) (0.1 * route.getBasePrice());
        }
        else if (scheduleRequest.getFlightClass().equals(Schedule.FlightClass.BUSINESS)) {
            netPrice += (int) (0.3 * route.getBasePrice());
        }

        Schedule result = Schedule.builder()
                .departureDate(scheduleRequest.getDepartureDate())
                .arrivalDate(scheduleRequest.getArrivalDate())
                .departureTime(scheduleRequest.getDepartureTime())
                .arrivalTime(scheduleRequest.getArrivalTime())
                .route(route)
                .netPrice(netPrice)
                .flightClass(scheduleRequest.getFlightClass())
                .flightDetail(flightDetail)
                .build();

        scheduleRepository.save(result);

        log.info("Has successfully created schedule data with ID : {}", result.getId());

        return result;
    }

    @Override
    public Schedule deleteSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully deleted schedule data with ID : {}", schedule.getId());

        scheduleRepository.delete(schedule);

        return schedule;
    }

    @Override
    public List<Schedule> getAllSchedules() {

        log.info("Has successfully retrieved all schedules data");

        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule>  getAllSchedulesByDefaultFilter(String departureCityName, String arrivalCityName, LocalDate departureDate, String flightClass, int page, int size, String[] sort) {

            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] sortSplit = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(sortSplit[1]), sortSplit[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));

            String departureCityNameConverted = convertToTitleCaseSplitting(departureCityName);
            String arrivalCityNameConverted = convertToTitleCaseSplitting(arrivalCityName);
            String flightClassConverted = flightClass.toUpperCase();

            Page<Schedule> schedulePage = scheduleRepository.findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClassOrderByNetPrice(departureCityNameConverted, arrivalCityNameConverted, departureDate, Schedule.FlightClass.valueOf(flightClassConverted), paging);

            log.info("Has successfully retrieved all schedules filtered by departure: {}, arrival: {}, departure date: {}, flight class: {}, with page = {}, size = {}, sort by {}", departureCityNameConverted, arrivalCityNameConverted, departureDate, flightClassConverted, page, size, sort);

            return schedulePage.stream().toList();
    }

    @Override
    public List<Schedule>  getAllSchedulesByDefaultFilterWithoutFlightClass(String departureCityName, String arrivalCityName, LocalDate departureDate, int page, int size, String[] sort) {

            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] sortSplit = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(sortSplit[1]), sortSplit[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            Pageable paging = PageRequest.of(page, size, Sort.by(orders));

            String departureCityNameConverted = convertToTitleCaseSplitting(departureCityName);
            String arrivalCityNameConverted = convertToTitleCaseSplitting(arrivalCityName);

            Page<Schedule> schedulePage = scheduleRepository.findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDate(departureCityNameConverted, arrivalCityNameConverted, departureDate, paging);

            log.info("Has successfully retrieved all schedules filtered by departure: {}, arrival: {}, departure date: {}, with page = {}, size = {}, sort by {}", departureCityNameConverted, arrivalCityNameConverted, departureDate, page, size, sort);

            return schedulePage.stream().toList();
    }

    @Override
    public List<Schedule> getAvailableSchedulesFilteredByDepartureTime(String departureCityName, String arrivalCityName, LocalDate departureDate, String flightClass, String timeRange, int page, int size, String[] sort) {

        LocalTime startTime;
        LocalTime endTime;

        switch (timeRange) {
            //Morning
            case "A" -> {
                startTime = LocalTime.parse("00:00:00");
                endTime = LocalTime.parse("11:00:00");
            }
            //Afternoon
            case "B" -> {
                startTime = LocalTime.parse("11:00:00");
                endTime = LocalTime.parse("15:00:00");
            }
            //Evening
            case "C" -> {
                startTime = LocalTime.parse("15:00:00");
                endTime = LocalTime.parse("18:30:00");
            }
            //Night
            case "D" -> {
                startTime = LocalTime.parse("18:30:00");
                endTime = LocalTime.parse("23:59:00");
            }
            //All
            default -> {
                startTime = LocalTime.parse("00:00:00");
                endTime = LocalTime.parse("23:59:00");
            }
        }

        log.info("Start Time: {}, End Time: {}", startTime, endTime);

        List<Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] sortSplit = sortOrder.split(",");
                orders.add(new Order(getSortDirection(sortSplit[1]), sortSplit[0]));
            }
        } else {
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        Pageable paging = PageRequest.of(page, size, Sort.by(orders));

        String departureCityNameConverted = convertToTitleCaseSplitting(departureCityName);
        String arrivalCityNameConverted = convertToTitleCaseSplitting(arrivalCityName);
        String flightClassConverted = flightClass.toUpperCase();

        Page<Schedule> schedulePage = scheduleRepository.findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClassAndDepartureTimeBetween(departureCityNameConverted, arrivalCityNameConverted, departureDate, Schedule.FlightClass.valueOf(flightClassConverted), startTime, endTime, paging);

        log.info("Has successfully retrieved all schedules filtered by departure: {}, arrival: {}, departure date: {}, flight class: {}, departure time between: {} - {}, with page = {}, size = {}, sort by {}", departureCityNameConverted, arrivalCityNameConverted, departureDate, flightClassConverted, startTime, endTime, page, size, sort);

        return schedulePage.stream().toList();
    }

    @Override
    public Schedule getScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully retrieved schedule data with ID : {}", schedule.getId());

        return schedule;
    }

    ModelMapper mapper = new ModelMapper();

    @Override
    public ScheduleDTO mapToDto(Schedule schedule) {
        return mapper.map(schedule, ScheduleDTO.class);
    }

    @Override
    public Schedule mapToEntity(ScheduleDTO scheduleDTO) {
        return mapper.map(scheduleDTO, Schedule.class);
    }

    private static final String WORD_SEPARATOR = " ";
    public static String convertToTitleCaseSplitting(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
                .stream(text.split(WORD_SEPARATOR))
                .map(word -> word.isEmpty()
                        ? word
                        : Character.toTitleCase(word.charAt(0)) + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(WORD_SEPARATOR));
    }

}
