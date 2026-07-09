package com.tech.soft.health_care_svc.staff.dto.request;

import com.tech.soft.health_care_svc.auth.enums.RoleType;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import com.tech.soft.health_care_svc.staff.enums.Department;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateStaffRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date Of Birth is required")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Department is required")
    private Department department;

    private RoleType designation;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    private String address;

    // User Details
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;


}