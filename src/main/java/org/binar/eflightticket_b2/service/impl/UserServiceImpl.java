package org.binar.eflightticket_b2.service.impl;


import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Role;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.RoleRepository;
import org.binar.eflightticket_b2.repository.UserRepository;
import org.binar.eflightticket_b2.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final Logger log =  LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ERROR  = "ERROR";
    private ModelMapper mapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    private static final String ROLE_USERS = "ROLE_USERS";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String INFO = "INFO  : ";

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findUsersByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Email Not Found");
                });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        users.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(users.getEmail(), users.getPassword(), authorities);
    }

    @Override
    public Users addUser(Users users, List<String> user) {
        if (userRepository.findUsersByEmail(users.getEmail()).isPresent()){
            log.info("email has taken");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put(ERROR, "email has taken");
            throw new BadRequestException(errorMessage);
        }

        List<String> reqRole = user;
        List<Role> roles = new LinkedList<>();

        if (reqRole == null){
            Role userRole = roleRepository.findRoleByName(ROLE_USERS).orElseThrow();
            log.info(INFO + users.getId() + " has assigned to ROLE_USER");
            roles.add(userRole);
        }else{
            reqRole.forEach(role -> {
                if (ROLE_ADMIN.equals(role)) {
                    Role adminRole = roleRepository.findRoleByName(ROLE_ADMIN).orElseThrow(
                            () -> {
                                log.error(ERROR + "ROLE_ADMIN NOT FOUND");
                                throw new ResourceNotFoundException("Role", "role", ROLE_ADMIN);
                            });
                    roles.add(adminRole);
                    log.info("Info : " + users.getId() + " has assigned to ROLE_ADMIN");
                } else {
                    Role userRole = roleRepository.findRoleByName(ROLE_USERS).orElseThrow(
                            () -> {
                                log.error(ERROR + "ROLE_USERS NOT FOUND");
                                throw new ResourceNotFoundException("Role", "role", ROLE_USERS);
                            });
                    roles.add(userRole);
                    log.info("Info : " + users.getId() + " has assigned to ROLE_USERS");
                }
            });
        }
        String encryptedPassword = bCryptPasswordEncoder.encode(users.getPassword());
        log.info("Info : Password has been encrypted" );
        users.setPassword(encryptedPassword);
        users.setRoles(roles);
        log.info("successfully persist data user to database");
        return userRepository.save(users);
    }

    @Override
    public Users deleteUser(Long id) {
        Users user = userRepository.findUsersById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException ex = new ResourceNotFoundException("id", id.toString(), String.class);
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
    public Users getUserById(Long id) {
        return userRepository.findUsersById(id).orElseThrow(() -> {
            ResourceNotFoundException ex = new ResourceNotFoundException("id", id.toString(), String.class);
            ex.setApiResponse();
            log.info(ex.getMessageMap().get("error"));
            throw ex;
        });
    }

    @Override
    public Users updateUser(Users users, Long id) {
        Users retrievedUser = userRepository.findUsersById(id).orElseThrow(() -> {
            ResourceNotFoundException ex = new ResourceNotFoundException("id", id.toString(), String.class);
            ex.setApiResponse();
            log.info(ex.getMessageMap().get("error"));
            throw ex;
        });
        boolean isEmailPresent = userRepository.findUsersByEmail(users.getEmail()).isPresent();
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
