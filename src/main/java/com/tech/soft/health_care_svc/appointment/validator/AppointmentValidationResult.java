package com.tech.soft.health_care_svc.appointment.validator;


import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class AppointmentValidationResult {

    private Patient patient;

    private Doctor doctor;

    private LocalTime appointmentEndTime;
}