package com.tech.soft.health_care_svc.doctor.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorSearchRequest {

    private String doctorCode;

    private String name;

    private String specialization;

    private String mobile;

    private Boolean active;
}