package com.moviedekho.userservie.controller;


import com.moviedekho.userservie.model.request.UserLoginRequest;
import com.moviedekho.userservie.model.request.UserRequest;
import com.moviedekho.userservie.model.response.UserLoginResponse;
import com.moviedekho.userservie.model.response.UserResponse;
import com.moviedekho.userservie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        UserResponse userResponse = userService.register(userRequest);
        return new ResponseEntity<>(userResponse,  HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginRequest user) {
       UserLoginResponse userLoginResponse = userService.authenticate(user.getUserName(), user.getPassword());
        return new ResponseEntity<>(userLoginResponse,  HttpStatus.OK);
    }
}