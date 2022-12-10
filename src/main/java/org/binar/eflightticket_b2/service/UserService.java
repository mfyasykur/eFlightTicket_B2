package org.binar.eflightticket_b2.service;


import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    Users addUser(Users users, List<String> role);
    Users deleteUser(Long id);
    Users getUserByEmail(String email);
    Users getUserById(Long id);
    Users updateUser(Users users, Long id);
    Users uploadImage(MultipartFile multipartFile, Long id) throws IOException;

    UsersDTO mapToDTO(Users users);
    Users mapToEntity(UsersDTO usersDTO);

    UserDetailRequest mapToUserDetailReq(Users users);
    Users mapToEntity(UserDetailRequest usersDTO);

}


