package org.binar.eflightticket_b2.service;


import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;

import java.util.List;

public interface UserService {

    Users addUser(Users users, List<String> role);
    Users deleteUser(Long id);
    Users getUserByEmail(String email);
    Users getUserById(Long id);
    Users updateUser(Users users, Long id);

    UsersDTO mapToDTO(Users users);
    Users mapToEntity(UsersDTO usersDTO);

    UserDetailRequest mapToUserDetailReq(Users users);
    Users mapToEntity(UserDetailRequest usersDTO);

}


