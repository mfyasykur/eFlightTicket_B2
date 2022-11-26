package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.repository.UserRepository;
import org.binar.eflightticket_b2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger log =  LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users addUsers(UsersDTO usersDTO) {
        if (userRepository.findUsersByUsername(usersDTO.getUsername()).isPresent()){
            log.info("username has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "username has taken");
            throw new BadRequestException(errorMessage);
        }
        if (userRepository.findUsersByEmail(usersDTO.getEmail()).isPresent()){
            log.info("email has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "email has taken");
            throw new BadRequestException(errorMessage);
        }
        Users users = Users.builder()
                .username(usersDTO.getUsername())
                .email(usersDTO.getEmail())
                .password(usersDTO.getPassword())
                .firstName(usersDTO.getFirstName())
                .lastName(usersDTO.getLastName())
                .build();
        log.info("successfully added data");
        return userRepository.save(users);
    }

}