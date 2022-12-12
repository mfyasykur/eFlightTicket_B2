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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("id") @NotBlank Long id){
        Users deletedUser = userService.deleteUser(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully delete user data with id : " +deletedUser.getId());
        log.info("successfully deleted user data  with id {} ", id);
        return new ResponseEntity<>(apiResponse, OK);
    }

    @GetMapping("users/get/{id}")
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

    @PutMapping("users/update")
    public ResponseEntity<ApiResponse> updateByUsername(@Valid @RequestBody UserDetailRequest newUsers,
                                                        @RequestParam @NotBlank Long id){
        Users users = userService.mapToEntity(newUsers);
        Users user = userService.updateUser(users, id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE
                , "Successfully updated data user with id : " +user.getId());
        log.info("Successfully updated data user with id {} ", user.getId());
        return new ResponseEntity<>(apiResponse, OK);
    }

    @PutMapping("users/upload")
    public ResponseEntity<ApiResponse> uploadImage(@Valid @RequestParam("id") Long id,
                                                   @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Users saveUser = userService.uploadImage(multipartFile, id);
        UsersDTO usersDTO = userService.mapToDTO(saveUser);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE
                , "Successfully uploaded profile picture user with id : " +saveUser.getId(), usersDTO);
        log.info("Successfully uploaded profile picture user with id {} ", saveUser.getId());
        return new ResponseEntity<>(apiResponse, OK);
    }




}
