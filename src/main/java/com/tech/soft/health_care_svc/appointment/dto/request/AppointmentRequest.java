package com.tech.soft.health_care_svc.appointment.dto.request;


import com.tech.soft.health_care_svc.appointment.enums.AppointmentType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotNull
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    @NotNull
    private AppointmentType appointmentType;

    @Size(max = 500)
    private String reason;

    private String remarks;
}