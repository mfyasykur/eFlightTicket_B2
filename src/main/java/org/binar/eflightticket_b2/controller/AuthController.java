package org.binar.eflightticket_b2.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.binar.eflightticket_b2.dto.LoginRequest;
import org.binar.eflightticket_b2.dto.UsersDTO;
import org.binar.eflightticket_b2.entity.Role;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.binar.eflightticket_b2.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;

    private final UserService userService;


    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            User user =(User) authenticate.getPrincipal();

        Users userByEmail = userService.getUserByEmail(loginRequest.getEmail());
        UsersDTO usersDTO = userService.mapToDTO(userByEmail);

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String accessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                    .withIssuer(request.getRequestURI())
                    .withClaim("roles", user.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm);
            log.info("Info :  successfully generated access token user");
            String refreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 70 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);
            log.info("Info :  successfully generated refresh token user");
            HashMap<String, Object> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            tokens.put("data", usersDTO);
            ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Successfully Login", tokens);
            return ResponseEntity.ok()
                    .header("access_token", accessToken)
                    .body(apiResponse);

    }

    @PostMapping("auth/signup")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UsersDTO user){
        Users users = userService.mapToEntity(user);
        List<String> role = user.getRole();
        Users savedUser = userService.addUser(users, role);
        user.setId(users.getId());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "Successfully added user data with id : " +savedUser.getId(), user);
        log.info("successfully added user data");
        return new ResponseEntity<>(apiResponse, CREATED);
    }

    @GetMapping(value = "auth/refresh")
    public ResponseEntity<ApiResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String email = decodedJWT.getSubject();
                Users userByEmail = userService.getUserByEmail(email);

                String accessToken = JWT.create()
                        .withSubject(userByEmail.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", userByEmail.getRoles().stream().map(Role::getName).toList())
                        .sign(algorithm);
                HashMap<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Successfully created new access token",tokens);
                return ResponseEntity.ok()
                        .body(apiResponse);

            }catch (Exception exception){
                HashMap<String, String> errorMessage = new HashMap<>();
                errorMessage.put("error", exception.getMessage());
                throw new BadRequestException(errorMessage);
            }
        }else {
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "refresh token is missing or not with valid form");
            throw new BadRequestException(errorMessage);
        }
    }

}
