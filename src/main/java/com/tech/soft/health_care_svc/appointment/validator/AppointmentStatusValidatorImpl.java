package com.tech.soft.health_care_svc.appointment.validator;

import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

@Component
public class AppointmentStatusValidatorImpl
        implements AppointmentStatusValidator {

    @Override
    public void validateTransition(
            AppointmentStatus current,
            AppointmentStatus next) {

        switch (current) {

            case BOOKED -> {

                if (next != AppointmentStatus.CHECKED_IN &&
                        next != AppointmentStatus.CANCELLED &&
                        next != AppointmentStatus.NO_SHOW) {

                    throw new InvalidRequestException(
                            "Invalid appointment status transition.");
                }

            }

            case CHECKED_IN -> {

                if (next != AppointmentStatus.CONSULTED) {

                    throw new InvalidRequestException(
                            "Invalid appointment status transition.");
                }

            }

            case CONSULTED -> {

                if (next != AppointmentStatus.BILLED) {

                    throw new InvalidRequestException(
                            "Invalid appointment status transition.");
                }

            }

            case BILLED -> {

                if (next != AppointmentStatus.PAID) {

                    throw new InvalidRequestException(
                            "Invalid appointment status transition.");
                }

            }

            case PAID -> {

                if (next != AppointmentStatus.CLOSED) {

                    throw new InvalidRequestException(
                            "Invalid appointment status transition.");
                }

            }

            default ->
                    throw new InvalidRequestException(
                            "Appointment cannot change status.");

        }

    }

}