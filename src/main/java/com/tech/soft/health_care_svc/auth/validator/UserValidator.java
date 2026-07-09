package com.tech.soft.health_care_svc.auth.validator;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.repository.RoleRepository;
import com.tech.soft.health_care_svc.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void validateCreate(CreateUserRequest request, Map<String, String> errors){
        if (userRepository.existsByUsername(
                request.getUsername())) {
            errors.put("username", "Username already exists : " + request.getUsername());
        }
        validateRoles(request.getRoles(),errors);
    }

    private void validateRoles(Set<String> roleCodes, Map<String, String> errors){
        if (roleCodes == null || roleCodes.isEmpty()) {
            errors.put("roles", "At least one role is required.");
            return;
        }

        List<String> invalidRoles = roleCodes.stream()
                .filter(roleCode -> roleRepository.findByRoleCode(roleCode).isEmpty())
                .toList();

        if (!invalidRoles.isEmpty()) {
            errors.put(
                    "roles",
                    "Invalid role(s): " + String.join(", ", invalidRoles));
        }
    }
}
