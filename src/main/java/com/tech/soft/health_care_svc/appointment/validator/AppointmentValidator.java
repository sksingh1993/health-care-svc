package com.tech.soft.health_care_svc.appointment.validator;


import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;

import java.util.Map;

public interface AppointmentValidator {

    AppointmentValidationResult validateCreate(
            AppointmentRequest request, Map<String, String> errors);

}