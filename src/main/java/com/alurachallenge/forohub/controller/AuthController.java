package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.AuthLoginRequest;
import com.alurachallenge.forohub.dto.RegisterDTO;
import com.alurachallenge.forohub.dto.UserDTO;
import com.alurachallenge.forohub.models.Role;
import com.alurachallenge.forohub.models.User;
import com.alurachallenge.forohub.services.RoleService;
import com.alurachallenge.forohub.services.UserService;
import com.alurachallenge.forohub.utils.JWTUtils;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JWTUtils jwtUtils, RoleService roleService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
    }



    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO register) {

        Role rol = roleService.findByName(register.roleName()).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        User user = new User(register);
        user.setRol(rol);
        try {
           return ResponseEntity.ok(new UserDTO(userService.registerUser(user)));
        } catch (AlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthLoginRequest userRequest, HttpServletResponse response) {

        Collection<? extends GrantedAuthority> authorities = userService.loadUserByUsername(userRequest.username()).getAuthorities();
        Authentication authToken = new UsernamePasswordAuthenticationToken(userRequest.username(),
                userRequest.password(),
                authorities);
        var userAuth = authenticationManager.authenticate(authToken);
        if(userAuth.isAuthenticated()){
            String accessToken = jwtUtils.createToken(authToken);
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(jwtUtils.getExpiration()* 3600L)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return ResponseEntity.ok("Logged in successfully!");
    }
}
