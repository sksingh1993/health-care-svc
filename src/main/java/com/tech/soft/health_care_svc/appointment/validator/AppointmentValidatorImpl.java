package com.tech.soft.health_care_svc.appointment.validator;

import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AvailableSlotResponse;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.appointment.repository.AppointmentRepository;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.InvalidRequestException;
import com.tech.soft.health_care_svc.common.util.DateUtils;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;

import com.tech.soft.health_care_svc.doctor.leave.repository.DoctorLeaveRepository;
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

    private final DoctorLeaveRepository leaveRepository;

    @Override
    public AppointmentValidationResult validateCreate(AppointmentRequest request,Map<String, String> errors) {

        Patient patient =
                patientValidator.validateActivePatient(
                        request.getPatientId(),errors);

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

            errors.put(
                    "appointmentExists",
                    "An appointment already exists for the selected patient with this doctor on the specified date and time.");
        }
        boolean isDoctorOnLeave = leaveRepository
                .existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndActiveTrue(
                        request.getDoctorId(),
                        request.getAppointmentDate(),
                        request.getAppointmentDate()
                );

        if(isDoctorOnLeave){
            errors.put(
                    "doctorOnLeave",
                    String.format("Doctor is on leave on date %s", DateUtils.convertDate(request.getAppointmentDate())));
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