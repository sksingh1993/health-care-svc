package com.tech.soft.health_care_svc.doctor.schedule.validator;

import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import org.apache.coyote.BadRequestException;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface DoctorScheduleValidator {

    Doctor validateCreate(Long doctorId,
                          DoctorScheduleRequest request) ;

    Doctor validateUpdate(Long doctorId,
                          Long scheduleId,
                          DoctorScheduleUpdateRequest request) ;

    DoctorSchedule validateSchedule(Long doctorId,
                                    Long scheduleId);

    void validateDoctorWorkingHours(Long doctorId,
                                    DayOfWeek dayOfWeek,
                                    LocalTime appointmentTime);
}