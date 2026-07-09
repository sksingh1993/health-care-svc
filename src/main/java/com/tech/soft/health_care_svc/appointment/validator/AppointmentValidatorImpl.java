package com.tech.soft.health_care_svc.appointment.validator;

import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AvailableSlotResponse;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.appointment.repository.AppointmentRepository;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;

import com.tech.soft.health_care_svc.doctor.schedule.service.DoctorScheduleService;
import com.tech.soft.health_care_svc.doctor.validator.DoctorValidator;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import com.tech.soft.health_care_svc.patient.validator.PatientValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppointmentValidatorImpl
        implements AppointmentValidator {

    private final PatientValidator patientValidator;

    private final DoctorValidator doctorValidator;

    private final DoctorScheduleService doctorScheduleService;

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentValidationResult validateCreate(
            AppointmentRequest request) {

        Patient patient =
                patientValidator.validateActivePatient(
                        request.getPatientId());

        Doctor doctor =
                doctorValidator.validateActiveDoctor(
                        request.getDoctorId());
        boolean exists = appointmentRepository
                .existsByDoctorIdAndPatientIdAndAppointmentDateAndAppointmentTime(
                        request.getDoctorId(),
                        request.getPatientId(),
                        request.getAppointmentDate(),
                        request.getAppointmentTime());

        if (exists) {
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Doctor already exists with email : "
                            + "email");
            throw new DuplicateResourceException(errors);
//            throw new DuplicateResourceException(String.format("Appointment already exists for doctor id: %d, patient id: %d, date and time.: %s & %s",
//                    doctor.getId(),patient.getId(),request.getAppointmentDate(),request.getAppointmentTime()));
        }
//TO DO
        List<AvailableSlotResponse> slots =
                doctorScheduleService.getAvailableSlots(
                        doctor.getId(),
                        request.getAppointmentDate());

        AvailableSlotResponse selectedSlot =
                slots.stream()
                        .filter(slot ->
                                slot.getStartTime()
                                        .equals(request.getAppointmentTime()))
                        .findFirst()
                        .orElseThrow(() ->
                                new InvalidRequestException(
                                        "Selected appointment slot is not available."));

        boolean isBooked = appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
                request.getDoctorId(), request.getAppointmentDate(), request.getAppointmentTime(), AppointmentStatus.BOOKED
        );
        if(!selectedSlot.isAvailable()){
            Map<String, String> errors = new HashMap<>();
            errors.put(
                    "email",
                    "Doctor already exists with email : "
                            + "email");
//            throw new DuplicateResourceException(
//                    "Selected slot is already booked.");
        }


        /*if (isBooked) {

            throw new DuplicateResourceException(
                    "Selected slot is already booked.");
        }*/


        return AppointmentValidationResult.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentEndTime(selectedSlot.getEndTime())
                .build();
    }
}