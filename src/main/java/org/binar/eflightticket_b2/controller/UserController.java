package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UsersDTO user){
        Users savedUser = userService.addUsers(user);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully added user data with id : " +savedUser.getId());
        log.info("successfully added user data");
        return new ResponseEntity<>(apiResponse, CREATED);
    }




}
