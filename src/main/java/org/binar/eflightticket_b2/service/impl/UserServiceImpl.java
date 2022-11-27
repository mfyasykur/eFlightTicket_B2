package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
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
    private static final String ERROR  = "ERROR";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users addUser(UsersDTO usersDTO) {
        if (userRepository.findUsersByUsername(usersDTO.getUsername()).isPresent()){
            log.info("username has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "username has taken");
            throw new BadRequestException(errorMessage);
        }
        if (userRepository.findUsersByEmail(usersDTO.getEmail()).isPresent()){
            log.info("email has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "email has taken");
            throw new BadRequestException(errorMessage);
        }
        Users users = Users.builder()
                .username(usersDTO.getUsername())
                .email(usersDTO.getEmail())
                .password(usersDTO.getPassword())
                .firstName(usersDTO.getFirstName())
                .lastName(usersDTO.getLastName())
                .build();
        log.info("successfully persist data user to database");
        return userRepository.save(users);
    }

    @Override
    public Users deleteUser(String username) {
        Users user = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> {
                    ResourceNotFoundException ex = new ResourceNotFoundException("username", username, String.class);
                    ex.setApiResponse();
                    log.info(ex.getMessageMap().get("error"));
                    throw ex;
                });
        userRepository.delete(user);
        log.info("succcessfully delete data user in database");
        return user;
    }

    @Override
    public UsersDTO getUserByUsername(String username) {
        Users user = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> {
                    ResourceNotFoundException ex = new ResourceNotFoundException("username", username, String.class);
                    ex.setApiResponse();
                    log.info(ex.getMessageMap().get("error"));
                    throw ex;
                });
        log.info("succcessfully retrieve data user in database");
        return UsersDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
