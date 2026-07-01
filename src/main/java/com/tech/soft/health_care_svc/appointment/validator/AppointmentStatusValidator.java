package com.tech.soft.health_care_svc.appointment.validator;

import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;

public interface AppointmentStatusValidator {

    void validateTransition(
            AppointmentStatus currentStatus,
            AppointmentStatus newStatus);

}