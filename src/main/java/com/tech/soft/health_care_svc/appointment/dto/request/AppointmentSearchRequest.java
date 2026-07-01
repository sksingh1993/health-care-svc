package com.tech.soft.health_care_svc.appointment.dto.request;


import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentSearchRequest {

    private Long patientId;

    private Long doctorId;

    private AppointmentStatus status;

    private LocalDate fromDate;

    private LocalDate toDate;
}