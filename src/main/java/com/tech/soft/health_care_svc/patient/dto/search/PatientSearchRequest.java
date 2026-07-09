package com.tech.soft.health_care_svc.patient.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientSearchRequest {

    private String patientCode;

    private String name;

    private String mobile;

    private Boolean active;
}