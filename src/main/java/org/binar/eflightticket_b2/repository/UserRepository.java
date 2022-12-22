package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUsersByEmail(String email);
    Optional<Users> findUsersById(Long id);

}
