package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

   Optional<List<Notification>> findAllByUsersId(Long id);

}
