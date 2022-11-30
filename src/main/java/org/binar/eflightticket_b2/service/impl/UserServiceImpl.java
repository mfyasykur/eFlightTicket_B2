package org.binar.eflightticket_b2.service.impl;


import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.UserRepository;
import org.binar.eflightticket_b2.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger log =  LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ERROR  = "ERROR";
    private ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Users addUser(Users users) {
        if (userRepository.findUsersByUsername(users.getUsername()).isPresent()){
            log.info("username has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "username has taken");
            throw new BadRequestException(errorMessage);
        }
        if (userRepository.findUsersByEmail(users.getEmail()).isPresent()){
            log.info("email has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "email has taken");
            throw new BadRequestException(errorMessage);
        }
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
    public Users getUserByUsername(String username) {
        Users user = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> {
                    ResourceNotFoundException ex = new ResourceNotFoundException("username", username, String.class);
                    ex.setApiResponse();
                    log.info(ex.getMessageMap().get("error"));
                    throw ex;
                });
        log.info("succcessfully retrieve data user in database");
        return user;
    }

    @Override
    public Users updateUser(Users users, String username) {
        Users retrievedUser = userRepository.findUsersByUsername(username).orElseThrow(() -> {
            ResourceNotFoundException ex = new ResourceNotFoundException("username", username, String.class);
            ex.setApiResponse();
            log.info(ex.getMessageMap().get("error"));
            throw ex;
        });
        boolean isUsernamePresent = userRepository.findUsersByUsername(users.getUsername()).isPresent();
        boolean isEmailPresent = userRepository.findUsersByEmail(users.getEmail()).isPresent();
        if (isUsernamePresent){
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "username has taken");
            throw new BadRequestException(errorMessage);
        }else {
            retrievedUser.setUsername(users.getUsername());
            retrievedUser.setPassword(users.getPassword());
            userRepository.save(retrievedUser);
        }
        if (isEmailPresent){
            log.info("email has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "email has taken");
            throw new BadRequestException(errorMessage);
        }else {
            retrievedUser.setEmail(users.getEmail());
            retrievedUser.setPassword(users.getPassword());
            userRepository.save(retrievedUser);
        }
        return retrievedUser;
    }

    @Override
    public UsersDTO mapToDTO(Users users) {
        return mapper.map(users, UsersDTO.class);
    }

    @Override
    public Users mapToEntity(UsersDTO usersDTO) {
        return mapper.map(usersDTO, Users.class);
    }

    @Override
    public UserDetailRequest mapToUserDetailReq(Users users) {
        return mapper.map(users, UserDetailRequest.class);
    }

    @Override
    public Users mapToEntity(UserDetailRequest usersDetailReq) {
        return mapper.map(usersDetailReq, Users.class);
    }


}
