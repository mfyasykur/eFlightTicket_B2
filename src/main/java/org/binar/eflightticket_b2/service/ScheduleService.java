package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.dto.ScheduleRequest;
import org.binar.eflightticket_b2.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule addSchedule(ScheduleRequest scheduleRequest);

    Schedule deleteSchedule(Long id);

    List<Schedule> getAllSchedules();

    Schedule getScheduleById(Long id);

    ScheduleDTO mapToDto(Schedule schedule);
    Schedule mapToEntity(ScheduleDTO scheduleDTO);
}
