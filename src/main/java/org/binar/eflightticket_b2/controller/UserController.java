package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.UserDetailRequest;
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

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("id") @NotBlank Long id){
        Users deletedUser = userService.deleteUser(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully delete user data with id : " +deletedUser.getId());
        log.info("successfully deleted user data  with id {} ", id);
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable ("id") @NotBlank Long id){
        Users user = userService.getUserById(id);
        UsersDTO userByUsername = userService.mapToDTO(user);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE
                , "Successfully retrieve user data with id : " +id
                , userByUsername);
        log.info("successfully retrieve user data with id {} ", id);
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateByUsername(@Valid @RequestBody UserDetailRequest newUsers,
                                                        @RequestParam @NotBlank String username){
        Users users = userService.mapToEntity(newUsers);
        Users user = userService.updateUser(users, username);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE
                , "Successfully updated data user with username : " +user.getUsername());
        log.info("Successfully updated data user with username {} ", user.getUsername());
        return new ResponseEntity<>(apiResponse, OK);
    }




}
