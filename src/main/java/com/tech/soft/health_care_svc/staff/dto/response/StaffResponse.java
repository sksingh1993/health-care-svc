package com.tech.soft.health_care_svc.staff.dto.response;


import com.tech.soft.health_care_svc.patient.enums.Gender;
import com.tech.soft.health_care_svc.staff.enums.Department;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StaffResponse {

    private Long id;

    private Long userId;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String mobile;

    private String email;

    private Department department;

    private String designation;

    private LocalDate joiningDate;

    private Boolean active;

    private Long version;
}