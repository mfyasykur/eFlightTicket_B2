package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
