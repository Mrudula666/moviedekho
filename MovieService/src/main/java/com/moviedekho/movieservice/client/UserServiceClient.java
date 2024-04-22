package com.moviedekho.movieservice.client;

import com.moviedekho.movieservice.model.request.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userservice", url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/api/auth/users/{username}/details")
    UserDetail getUserDetails(@PathVariable("username") String username);
}
