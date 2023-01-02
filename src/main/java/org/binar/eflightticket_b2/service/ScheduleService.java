package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    Schedule addSchedule(ScheduleRequest scheduleRequest);

    Schedule deleteSchedule(Long id);

    List<Schedule> getAllSchedules();

    List<Schedule> getAllSchedulesByDefaultFilter(String departureCityName, String arrivalCityName, LocalDate departureDate, String flightClass, int page, int size, String[] sort);

    List<Schedule> getAvailableSchedulesFilteredByDepartureTime(String departureCityName, String arrivalCityName, LocalDate departureDate, String flightClass, String timeRange, int page, int size, String[] sort);

    List<Schedule> getAllSchedulesByDefaultFilterWithoutFlightClass(String departureCityName, String arrivalCityName, LocalDate departureDate, int page, int size, String[] sort);

    Schedule getScheduleById(Long id);

    ScheduleDTO mapToDto(Schedule schedule);
    Schedule mapToEntity(ScheduleDTO scheduleDTO);
}
