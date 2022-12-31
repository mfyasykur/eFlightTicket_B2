package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {

        Schedule schedule = scheduleService.addSchedule(scheduleRequest);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully add schedule with id : " + schedule.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllSchedules() {

        List<ScheduleDTO> result = scheduleService.getAllSchedules().stream().map(schedule -> scheduleService.mapToDto(schedule))
                .collect(Collectors.toList());

        if (!result.isEmpty()) {

            ApiResponse apiResponse = new ApiResponse(
                    Boolean.TRUE,
                    "successfully retrieved all schedules",
                    result
            );

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Schedule data is empty. Please add schedule first", result), HttpStatus.OK);
    }

    @GetMapping("/get/all/search/default")
    public ResponseEntity<ApiResponse> getAllSchedulesByDefaultFilter(
            @RequestParam("departure") String departureCityName,
            @RequestParam("arrival") String arrivalCityName,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(value = "class", defaultValue = "ECONOMY") String flightClass,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "departureTime,asc") String[] sort) {

            List<Schedule> schedules = scheduleService.getAllSchedulesByDefaultFilter(departureCityName, arrivalCityName, departureDate, flightClass, page, size, sort);
            List<ScheduleDTO> result = schedules.stream().map(schedule -> scheduleService.mapToDto(schedule)).toList();

            if (!result.isEmpty()) {

                ApiResponse apiResponse = new ApiResponse(
                        Boolean.TRUE,
                        "successfully retrieved all schedules searched by departure: " + departureCityName + ", arrival: " + arrivalCityName + ", date: " + departureDate + ", flight class: " + flightClass + ", with page = " + page + ", size = " + size + ", sort by " + Arrays.toString(sort),
                        result
                );

                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }

            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Flight not found. Please choose another schedule.", result), HttpStatus.OK);
    }

    @GetMapping("/get/all/search/")
    public ResponseEntity<ApiResponse> getAllSchedulesByDefaultFilterWithoutFlightClass(
            @RequestParam("departure") String departureCityName,
            @RequestParam("arrival") String arrivalCityName,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "netPrice,asc") String[] sort) {

        List<Schedule> schedules = scheduleService.getAllSchedulesByDefaultFilterWithoutFlightClass(departureCityName, arrivalCityName, departureDate, page, size, sort);
        List<ScheduleDTO> result = schedules.stream().map(schedule -> scheduleService.mapToDto(schedule)).toList();

        if (!result.isEmpty()) {

            ApiResponse apiResponse = new ApiResponse(
                    Boolean.TRUE,
                    "successfully retrieved all schedules searched by departure: " + departureCityName + ", arrival: " + arrivalCityName + ", date: " + departureDate + ", with page = " + page + ", size = " + size + ", sort by " + Arrays.toString(sort),
                    result
            );

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Flight not found. Please choose another schedule.", result), HttpStatus.OK);
    }

    @GetMapping("/get/all/filter/time")
    public ResponseEntity<ApiResponse> getAvailableSchedulesFilteredByDepartureTime(
            @RequestParam(value = "departure") String departureCityName,
            @RequestParam(value = "arrival") String arrivalCityName,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(value = "class", defaultValue = "ECONOMY") String flightClass,
            @RequestParam(value = "timeType") String timeRange,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "departureTime,asc") String[] sort) {

        List<Schedule> schedules = scheduleService.getAvailableSchedulesFilteredByDepartureTime(departureCityName, arrivalCityName, departureDate, flightClass, timeRange, page, size, sort);
        List<ScheduleDTO> result = schedules.stream().map(schedule -> scheduleService.mapToDto(schedule)).toList();

        if (!result.isEmpty()) {

            ApiResponse apiResponse = new ApiResponse(
                    Boolean.TRUE,
                    "successfully retrieved all schedules searched by departure: " + departureCityName + ", arrival: " + arrivalCityName + ", date: " + departureDate + ", flight class: " + flightClass + ", filtered by time range type: " + timeRange + ", with page = " + page + ", size = " + size + ", sort by " + Arrays.toString(sort),
                    result
            );

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Flight not found. Please choose another schedule.", result), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getScheduleById(@PathVariable Long id) {

        Schedule schedule = scheduleService.getScheduleById(id);
        ScheduleDTO result = scheduleService.mapToDto(schedule);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved schedule with id : " + schedule.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSchedule(@PathVariable Long id) {

        Schedule result = scheduleService.deleteSchedule(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted schedule with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
