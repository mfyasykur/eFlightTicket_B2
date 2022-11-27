package org.binar.eflightticket_b2.service;


import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;

public interface UserService {

    Users addUser(UsersDTO usersDTO);
    Users deleteUser(String username);

}
