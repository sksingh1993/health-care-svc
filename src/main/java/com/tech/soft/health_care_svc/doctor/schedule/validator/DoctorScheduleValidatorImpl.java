package com.tech.soft.health_care_svc.doctor.schedule.validator;


import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import com.tech.soft.health_care_svc.doctor.schedule.repository.DoctorScheduleRepository;
import com.tech.soft.health_care_svc.doctor.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DoctorScheduleValidatorImpl
        implements DoctorScheduleValidator {

    private final DoctorValidator doctorValidator;

    private final DoctorScheduleRepository repository;

    @Override
    public Doctor validateCreate(Long doctorId,
                                 DoctorScheduleRequest request){

        Doctor doctor = doctorValidator.validateActiveDoctor(doctorId);
        validateTime(request.getStartTime(), request.getEndTime());
        validateOverlap(doctorId, request.getDayOfWeek(), request.getStartTime(), request.getEndTime());
        return doctor;
    }

    @Override
    public Doctor validateUpdate(Long doctorId, Long scheduleId, DoctorScheduleUpdateRequest request) {

        validateSchedule(doctorId, scheduleId);

        Doctor doctor = doctorValidator.validateActiveDoctor(doctorId);

        validateTime(request.getStartTime(), request.getEndTime());

        if (repository
                .existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThanAndIdNotAndActiveTrue(
                        doctorId, request.getDayOfWeek(), request.getEndTime(), request.getStartTime(),
                        scheduleId)) {
            throw new DuplicateResourceException("Schedule overlaps with existing schedule.");
        }

        return doctor;
    }

    @Override
    public DoctorSchedule validateSchedule(Long doctorId, Long scheduleId) {

        DoctorSchedule schedule =
                repository.findByIdAndActiveTrue(scheduleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor Schedule",
                                        scheduleId));

        if (!schedule.getDoctor().getId().equals(doctorId)) {
            throw new ResourceNotFoundException(
                    String.format("Schedule %d does not belong to doctor", scheduleId),doctorId);
        }
        return schedule;
    }

    @Override
    public void validateDoctorWorkingHours(Long doctorId, java.time.DayOfWeek dayOfWeek, LocalTime appointmentTime) {

    }

    private void validateTime(LocalTime start, LocalTime end) {

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    private void validateOverlap(Long doctorId, DayOfWeek day, LocalTime start, LocalTime end) {

        if (repository.existsByDoctorIdAndDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThanAndActiveTrue(
                        doctorId, day, end, start)) {
            throw new DuplicateResourceException("Schedule overlaps with existing schedule.");
        }
    }

    /*@Override
    public void validateDoctorWorkingHours(
            Long doctorId,
            DayOfWeek day,
            LocalTime appointmentTime) {

        // Used later in Appointment Module
    }*/
}