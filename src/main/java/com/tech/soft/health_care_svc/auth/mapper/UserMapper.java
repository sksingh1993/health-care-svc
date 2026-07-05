package com.tech.soft.health_care_svc.auth.mapper;

import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.LoginResponse;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.Role;
import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.enums.RoleType;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active",constant = "true")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(CreateUserRequest request);

    //@Mapping(target = "id",ignore = true)
    @Mapping(target = "roles", ignore = true)
    CreateUserRequest toUserRequest(DoctorRequest doctorRequest);

    @Mapping(target = "roles",
    expression = "java(getRole(request.getRole()))")
    @Mapping(target = "username",source = "userName")
    CreateUserRequest toUserRequest(CreateStaffRequest request);

    @Mapping(target = "roles",
    expression = "java(getRoles(user))")
    UserResponse toResponse(User user);

    @Mapping(target = "roles",
            expression = "java(getRoles(user))")
    @Mapping(target = "userId",source = "id")
    @Mapping(target = "userName",source = "username")
    @Mapping(target = "fullName",
    expression = "java(getFullName(user))")
    LoginResponse toLoginResponse(User user);

    default Set<String> getRoles(User user){
        return user.getRoles().stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toSet());
    }
    default Set<String> getRole(RoleType role){
        return Set.of(role.name());
    }
    default String getFullName(User user){
        return user.getFirstName()+" "+user.getLastName();
    }
}
