package com.tech.soft.health_care_svc.auth.service.impl;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.mapper.UserMapper;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import com.tech.soft.health_care_svc.auth.service.UserService;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;


    public UserResponse createUser(
            CreateUserRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists");
        }

        Set<Role> roles = getRoles(request);

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
        /*User user = User.builder()
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
                .build();*/

        User saved =
                userRepository.save(user);
        return  mapper.toResponse(saved);

        /*return UserResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .roles(
                        saved.getRoles()
                                .stream()
                                .map(Role::getRoleCode)
                                .collect(Collectors.toSet()))
                .build();*/
    }
    public User createDiffrentTypeOfUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("duplicateUser","Username already exists");
            throw new DuplicateResourceException(validationErrors);
        }

        Set<Role> roles = getRoles(request);

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);
                return userRepository.save(user);
    }

    private @NonNull Set<Role> getRoles(CreateUserRequest request) {
        Set<Role> roles =
                request.getRoles()
                        .stream()
                        .map(roleCode ->
                                roleRepository.findByRoleCode(roleCode)
                                        .orElseThrow(() ->
                                                new RuntimeException("Role not found: " + roleCode)))
                        .collect(Collectors.toSet());
        return roles;
    }
}