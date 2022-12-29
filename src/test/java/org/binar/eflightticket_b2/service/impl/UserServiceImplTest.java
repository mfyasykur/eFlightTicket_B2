package org.binar.eflightticket_b2.service.impl;

import com.cloudinary.Cloudinary;
import org.binar.eflightticket_b2.dto.UserDetailRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Notification;
import org.binar.eflightticket_b2.entity.Role;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.repository.NotificationRepository;
import org.binar.eflightticket_b2.repository.RoleRepository;
import org.binar.eflightticket_b2.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    Cloudinary cloudinary;

    @Mock
    ModelMapper modelMapper;

    @Mock
    NotificationServiceImpl notificationService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationServiceImpl(notificationRepository);
    }

    @Test
    @Disabled
    void loadUserByUsername() {
    }

    @Test
    void registerUserSuccess() {
        Role userRole = new Role();
        userRole.setId(1l);
        userRole.setName("ROLE_USERS");

        UsersDTO userDTO = UsersDTO.builder()
                .id(111L)
                .firstName("haris")
                .lastName("aulia")
                .email("haris.aulia@gmail.com")
                .password("some-password")
                .build();

        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");
        List<String> roleUsers = List.of("ROLE_USERS");

        when(userRepository.findUsersByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        when(roleRepository.findRoleByName("ROLE_USERS")).thenReturn(Optional.of(userRole));

        when(userRepository.save(any(Users.class))).thenReturn(users);

        Users savedUser = userService.addUser(users, roleUsers);
        System.out.println(savedUser);
        Assertions.assertEquals(userDTO.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(roleUsers.get(0), savedUser.getRoles().get(0).getName());
        verify(userRepository, times(1)).findUsersByEmail(userDTO.getEmail());
        verify(userRepository, times(1)).save(users);
        verify(roleRepository, times(1)).findRoleByName("ROLE_USERS");
    }

    @Test
    void deleteUserSuccess() {
        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        when(userRepository.findUsersById(anyLong())).thenReturn(Optional.of(users));

        Users deletedUser = userService.deleteUser(anyLong());

        Assertions.assertEquals(users.getEmail(), deletedUser.getEmail());
        verify(userRepository, times(1)).delete(users);
    }

    @Test
    void getUserByEmailSuccess() {
        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        when(userRepository.findUsersByEmail("haris.aulia@gmail.com")).thenReturn(Optional.of(users));

        Users retrievedUser = userService.getUserByEmail("haris.aulia@gmail.com");
        Assertions.assertEquals(users.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findUsersByEmail("haris.aulia@gmail.com");
    }

    @Test
    void getUserById() {
        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        when(userRepository.findUsersById(anyLong())).thenReturn(Optional.of(users));

        Users retrievedUser = userService.getUserById(111l);
        Assertions.assertEquals(users.getEmail(), retrievedUser.getEmail());
        verify(userRepository, times(1)).findUsersById(111l);
    }

    @Test
    void updateUserSuccess() {

        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        when(userRepository.findUsersById(anyLong())).thenReturn(Optional.of(users));
        when(userRepository.save(any(Users.class))).thenReturn(users);

        Users updatedUser = userService.updateUser(users, 111l);
        Assertions.assertEquals(users.getEmail(), updatedUser.getEmail());
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