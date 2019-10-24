package com.nectcracker.studyproject.domain;

import lombok.*;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@NonNull
public class UserRegistrationRequest {
    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private Date birthday;
}
