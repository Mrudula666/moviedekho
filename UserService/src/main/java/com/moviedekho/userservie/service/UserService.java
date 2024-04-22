package com.moviedekho.userservie.service;


import com.moviedekho.userservie.model.request.UserRequest;
import com.moviedekho.userservie.model.response.UserLoginResponse;
import com.moviedekho.userservie.model.response.UserResponse;

public interface UserService {

    public UserResponse register(UserRequest userRequest) throws Exception;

    public UserLoginResponse authenticate(String username, String password);

    UserLoginResponse getUserDetails(String username);
}
