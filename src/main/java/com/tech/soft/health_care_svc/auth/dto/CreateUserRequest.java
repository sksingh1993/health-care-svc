package com.tech.soft.health_care_svc.auth.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {


    private String username;


    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;


    private Set<String> roles;
}