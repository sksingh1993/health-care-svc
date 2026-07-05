package com.tech.soft.health_care_svc.auth.service.impl;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.enums.RoleType;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import com.tech.soft.health_care_svc.auth.service.UserManagementService;
import com.tech.soft.health_care_svc.auth.service.UserService;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl
        implements UserManagementService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final UserService userService;

    @Override
    public User createDoctorUser(CreateUserRequest userRequest) {
        userRequest.setRoles(Set.of(RoleType.DOCTOR.name()));

        return userService.createDiffrentTypeOfUser(userRequest);
    }
    @Override
    public User createStaffUser(CreateUserRequest userRequest) {

        return userService.createDiffrentTypeOfUser(userRequest);
    }
//Modify later when ReceptionistUser created
    @Override
    public User createReceptionistUser(
            String username,
            String password) {

        return createUser(
                username,
                password,
                RoleType.RECEPTIONIST);
    }

    @Override
    public User createPatientUser(
            String username,
            String password) {

        return createUser(
                username,
                password,
                RoleType.PATIENT);
    }

    private User createUser(
            String username,
            String password,
            RoleType roleType) {

        if (userRepository.existsByUsername(username)) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Doctor already exists with email : "
                            + "email");

            //throw new DuplicateResourceException("Username already exists");
        }

        Role role = roleRepository.findByRoleCode(roleType.name())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Role not found : " + roleType));
        Set<Role> roles = Set.of(role);
        User user = new User();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password));

        user.setRoles(roles);

        return userRepository.save(user);
    }
}