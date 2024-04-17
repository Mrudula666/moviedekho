package com.moviedekho.userservie.model.response;

import com.moviedekho.userservie.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserLoginResponse extends GenericResponse{

    private String token;
    private String email;
    private String mobileNumber;
    private RoleName roleName;
    private String username;
    private LocalDate dateOfBirth;
    private String gender;
    private String country;
    private String type = "Bearer ";
}
