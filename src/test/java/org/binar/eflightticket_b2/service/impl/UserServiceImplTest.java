package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void addUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void uploadImage() {
    }

    @Test
    void multipartToFile() {
    }

    @Test
    void mapToDTO() {
    }

    @Test
    void mapToEntity() {
    }

    @Test
    void mapToUserDetailReq() {
    }

    @Test
    void testMapToEntity() {
    }
}