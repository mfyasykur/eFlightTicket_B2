package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
