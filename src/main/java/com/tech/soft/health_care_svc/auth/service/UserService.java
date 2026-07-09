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


//@Transactional
public interface UserService {
    public UserResponse createUser(CreateUserRequest request);
    User createDiffrentTypeOfUser(CreateUserRequest request);
}