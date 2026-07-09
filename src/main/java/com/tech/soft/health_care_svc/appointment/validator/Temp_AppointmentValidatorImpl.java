/*
package com.tech.soft.health_care_svc.appointment.validator;

import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AvailableSlotResponse;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;

import com.tech.soft.health_care_svc.doctor.schedule.service.DoctorScheduleService;
import com.tech.soft.health_care_svc.doctor.validator.DoctorValidator;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import com.tech.soft.health_care_svc.patient.validator.PatientValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Temp_AppointmentValidatorImpl
        implements AppointmentValidator {

    private final PatientValidator patientValidator;

    private final DoctorValidator doctorValidator;

    private final DoctorScheduleService doctorScheduleService;

    @Override
    public AppointmentValidationResult validateCreate(
            AppointmentRequest request) {

        Patient patient =
                patientValidator.validateActivePatient(
                        request.getPatientId());

        Doctor doctor =
                doctorValidator.validateActiveDoctor(
                        request.getDoctorId());
//TO DO
        AvailableSlotResponse slot =
                doctorScheduleService
                        .findAvailableSlot(
                                doctor.getId(),
                                request.getAppointmentDate(),
                                request.getAppointmentTime())
                        .orElseThrow(() ->
                                new InvalidRequestException(
                                        "Invalid appointment slot."));

        if (!slot.isAvailable()) {

            throw new DuplicateResourceException(
                    "Selected slot is already booked.");
        }

        return AppointmentValidationResult.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentEndTime(slot.getEndTime())
                .build();
    }
}*/
