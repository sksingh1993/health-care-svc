package com.tech.soft.health_care_svc.staff.dto.request;

import com.tech.soft.health_care_svc.staff.enums.Department;
import lombok.Data;

@Data
public class StaffSearchRequest {

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String mobile;

    private Department department;

    private Boolean active;
}