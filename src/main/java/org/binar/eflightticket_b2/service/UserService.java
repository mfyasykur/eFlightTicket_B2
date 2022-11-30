package org.binar.eflightticket_b2.service;


import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;

public interface UserService {

    Users addUser(Users users);
    Users deleteUser(String username);
    Users getUserByUsername (String username);
    Users updateUsername(Users users, String newUsername);

    UsersDTO mapToDTO(Users users);
    Users mapToEntity(UsersDTO usersDTO);

}


