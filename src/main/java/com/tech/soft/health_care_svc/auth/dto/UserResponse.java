package com.tech.soft.health_care_svc.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Set<String> roles;
}