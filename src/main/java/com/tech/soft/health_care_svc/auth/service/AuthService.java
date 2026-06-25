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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        Authentication authenticate = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()));

        String token =
                jwtService.generateToken(request.getUserName());

        return new LoginResponse(token);
    }

    public UserResponse createAdmin(
            CreateUserRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists");
        }

        Set<Role> roles =
                request.getRoles()
                        .stream()
                        .map(roleCode ->
                                roleRepository
                                        .findByRoleCode(roleCode)
                                        .orElseThrow(() ->
                                                new RuntimeException(
                                                        "Role not found: "
                                                                + roleCode)))
                        .collect(Collectors.toSet());

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .active(true)
                .roles(roles)
                .build();

        User saved =
                userRepository.save(user);

        return UserResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .roles(
                        saved.getRoles()
                                .stream()
                                .map(Role::getRoleCode)
                                .collect(Collectors.toSet()))
                .build();
    }
}