package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
