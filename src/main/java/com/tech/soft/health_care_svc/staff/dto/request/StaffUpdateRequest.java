package com.tech.soft.health_care_svc.staff.dto.request;


import com.tech.soft.health_care_svc.auth.enums.RoleType;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import com.tech.soft.health_care_svc.staff.enums.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffUpdateRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Department is required")
    private Department department;

    @NotNull(message = "Role is required")
    private RoleType designation;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Version is required")
    private Long version;
}