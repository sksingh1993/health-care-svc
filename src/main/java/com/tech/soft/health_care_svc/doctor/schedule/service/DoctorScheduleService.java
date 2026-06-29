package com.tech.soft.health_care_svc.doctor.schedule.service;


import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleSearchRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.response.DoctorScheduleResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorScheduleService {

    DoctorScheduleResponse createSchedule(
            Long doctorId,
            DoctorScheduleRequest request) ;

    DoctorScheduleResponse updateSchedule(
            Long doctorId,
            Long scheduleId,
            DoctorScheduleUpdateRequest request);

    DoctorScheduleResponse getSchedule(
            Long doctorId,
            Long scheduleId);

    Page<DoctorScheduleResponse> searchSchedules(
            Long doctorId,
            DoctorScheduleSearchRequest request,
            Pageable pageable);

    void deleteSchedule(
            Long doctorId,
            Long scheduleId);
}