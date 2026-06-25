package com.tech.soft.health_care_svc.auth.service;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponse createUser(
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