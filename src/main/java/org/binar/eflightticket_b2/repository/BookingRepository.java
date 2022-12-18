package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<List<Booking>> findAllByUsersIdAndIsSuccess(Long userId, Boolean isSuccess);

}
