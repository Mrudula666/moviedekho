package com.moviedekho.userservie.service;


import com.moviedekho.userservie.entity.User;

public interface UserService {

    public User register(User user);

    public User authenticate(String username, String password);
}
