package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.ScheduleDTO;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.ScheduleRepository;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private static final String ENTITY = "schedule";

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule addSchedule(Schedule schedule) {

        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(Long id, Schedule schedule) {

        Schedule result = scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setDepartureDate(schedule.getDepartureDate());
        result.setArrivalDate(schedule.getArrivalDate());
        result.setNetPrice(schedule.getNetPrice());
        result.setRoute(schedule.getRoute());
        result.setFlightDetail(schedule.getFlightDetail());
        scheduleRepository.save(result);

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
        scheduleRepository.delete(schedule);

        return schedule;
    }

    @Override
    public List<Schedule> getAllSchedules() {

        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Long id) {

        return scheduleRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
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

}
