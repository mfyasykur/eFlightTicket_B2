package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleFilterResponse;
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

        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all schedules",
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all/filter/default")
    public ResponseEntity<ApiResponse> getAllSchedulesByDefaultFilter(
            @RequestParam String departureCityName,
            @RequestParam String arrivalCityName,
            @RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam Schedule.FlightClass flightClass,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

            List<Schedule> schedules = scheduleService.getAllSchedulesByDefaultFilter(departureCityName, arrivalCityName, departureDate, flightClass, page, size);
            List<ScheduleDTO> result = schedules.stream().map(schedule -> scheduleService.mapToDto(schedule)).toList();

            ApiResponse apiResponse = new ApiResponse(
                    Boolean.TRUE,
                    "successfully retrieved all schedules filtered by departure: " + departureCityName + ", arrival: " + arrivalCityName + ", date: " + departureDate + ", flight class: " + flightClass + ", with page = " + page + ", size = " + size,
                    result
            );

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all/filter/departure")
    public ResponseEntity<ApiResponse> getAllSchedulesByDeparture(
            @RequestParam String departureCityName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        List<Schedule> schedules = scheduleService.getAllSchedulesByDeparture(departureCityName, page, size);
        List<ScheduleDTO> result = schedules.stream().map(schedule -> scheduleService.mapToDto(schedule)).toList();

        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all schedules filtered by departure: " + departureCityName + ", with page = " + page + ", size = " + size,
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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
