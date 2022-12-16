package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleFilterResponse;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.Schedule;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    Schedule addSchedule(ScheduleRequest scheduleRequest);

    Schedule deleteSchedule(Long id);

    List<Schedule> getAllSchedules();

    List<Schedule> getAllSchedulesByDefaultFilter(String departureCityName, String arrivalCityName, LocalDate departureDate, Schedule.FlightClass flightClass, int page, int size);

    List<Schedule> getAllSchedulesByDeparture(String departureCityName, int page, int size);

    Schedule getScheduleById(Long id);

//    List<ScheduleFilterResponse> getSchedulesByDate(LocalDate departureDate);
//
//    List<Schedule> getSchedulesByTime(LocalTime departureTime);
//
//    List<Schedule> getSchedulesByPrice(Integer price);

    ScheduleDTO mapToDto(Schedule schedule);
    Schedule mapToEntity(ScheduleDTO scheduleDTO);
}
