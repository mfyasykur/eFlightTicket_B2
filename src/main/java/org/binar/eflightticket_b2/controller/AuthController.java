package org.binar.eflightticket_b2.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.binar.eflightticket_b2.dto.LoginRequest;
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
import javax.validation.Valid;

import java.util.Date;
import java.util.HashMap;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            User user =(User) authenticate.getPrincipal();

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
            HashMap<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Successfully Login", tokens);
            return ResponseEntity.ok()
                    .header("access_token", accessToken)
                    .body(apiResponse);

    }

}
