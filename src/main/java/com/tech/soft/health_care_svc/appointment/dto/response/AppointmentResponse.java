package com.tech.soft.health_care_svc.appointment.dto.response;


import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;

    private String appointmentCode;

    private Long patientId;

    private String patientCode;

    private String patientName;

    private Long doctorId;

    private String doctorCode;

    private String doctorName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private LocalTime appointmentEndTime;

    private AppointmentStatus status;

    private AppointmentType appointmentType;

    private String reason;

    private String remarks;
}