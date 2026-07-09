package com.tech.soft.health_care_svc.appointment.validator;


import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;

public interface AppointmentValidator {

    AppointmentValidationResult validateCreate(
            AppointmentRequest request);

}