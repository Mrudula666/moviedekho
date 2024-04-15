package com.moviedekho.userservie.serviceimpl;

import com.moviedekho.userservie.entity.User;
import com.moviedekho.userservie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public User authenticate(String username, String password) {
        return null;
    }
}
