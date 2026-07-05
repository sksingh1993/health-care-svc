package com.tech.soft.health_care_svc.auth.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private Long userId;
    private String userName;
    private String fullName;
    private Set<String> roles;
}
