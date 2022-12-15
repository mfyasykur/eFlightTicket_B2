package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
