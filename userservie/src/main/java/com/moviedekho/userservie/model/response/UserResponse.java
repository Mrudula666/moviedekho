package com.moviedekho.userservie.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends GenericResponse {

    private String email;
    private String mobileNumber;
    private String role;
    private String username;
    private String dateOfBirth;
    private String gender;
    private String country;
    private String subscriptionPlan;

}
