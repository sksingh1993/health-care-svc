package com.tech.soft.health_care_svc.auth.service;

import com.tech.soft.health_care_svc.auth.dto.*;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import com.tech.soft.health_care_svc.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


public interface AuthService {

    public LoginResponse login(LoginRequest request);

    public UserResponse createAdmin(CreateUserRequest request) ;
}