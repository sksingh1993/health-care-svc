package com.tech.soft.health_care_svc.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDropdownResponse {

    private Long value;

    private String label;

    private String specialization;

}