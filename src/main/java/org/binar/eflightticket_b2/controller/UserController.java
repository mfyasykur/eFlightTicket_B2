package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
        Users users = userService.mapToEntity(user);
        Users savedUser = userService.addUser(users);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully added user data with id : " +savedUser.getId());
        log.info("successfully added user data");
        return new ResponseEntity<>(apiResponse, CREATED);
    }


    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("username") @NotBlank String username){
        Users deletedUser = userService.deleteUser(username);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully delete user data with username : " +deletedUser.getUsername());
        log.info("successfully deleted user data  with username {} ", username);
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable ("username") @NotBlank String username){
        UsersDTO userByUsername = userService.mapToDTO(userService.getUserByUsername(username));
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE
                , "Successfully retrieve user data with username : " +userByUsername.getUsername()
                , userByUsername);
        log.info("successfully retrieve user data with username {} ", userByUsername.getUsername());
        return new ResponseEntity<>(apiResponse, OK);
    }




}
