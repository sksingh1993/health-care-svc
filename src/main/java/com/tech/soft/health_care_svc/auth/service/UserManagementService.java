package com.tech.soft.health_care_svc.auth.service;


import com.tech.soft.health_care_svc.auth.dto.CreateUserRequest;
import com.tech.soft.health_care_svc.auth.dto.UserResponse;
import com.tech.soft.health_care_svc.auth.entity.User;

public interface UserManagementService {

    User createDoctorUser(CreateUserRequest userRequest);
    public User createStaffUser(CreateUserRequest userRequest);
    User createReceptionistUser(
            String username,
            String password);

    User createPatientUser(
            String username,
            String password);
}