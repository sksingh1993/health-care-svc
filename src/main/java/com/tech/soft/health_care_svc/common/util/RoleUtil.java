package com.tech.soft.health_care_svc.common.util;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RoleUtil {
    private final RoleRepository roleRepository;
    public  @NonNull Set<Role> getRoles(CreateUserRequest request) {
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
