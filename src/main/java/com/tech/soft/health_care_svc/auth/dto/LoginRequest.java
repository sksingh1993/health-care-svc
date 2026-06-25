package com.tech.soft.health_care_svc.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
