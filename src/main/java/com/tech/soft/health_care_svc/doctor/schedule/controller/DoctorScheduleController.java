package com.tech.soft.health_care_svc.doctor.schedule.controller;

import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleSearchRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.response.DoctorScheduleResponse;
import com.tech.soft.health_care_svc.doctor.schedule.service.DoctorScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors/{doctorId}/schedules")
public class DoctorScheduleController {

    private final DoctorScheduleService service;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> createSchedule(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorScheduleRequest request)  {

        return ResponseEntity.ok(ApiResponse.success(
                "Doctor schedule created successfully",
                service.createSchedule(doctorId, request)));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> updateSchedule(
            @PathVariable Long doctorId,
            @PathVariable Long scheduleId,
            @Valid @RequestBody DoctorScheduleUpdateRequest request)  {

        return ResponseEntity.ok(ApiResponse.success(
                "Doctor schedule updated successfully",
                service.updateSchedule(
                        doctorId,
                        scheduleId,
                        request)));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<DoctorScheduleResponse>> getSchedule(
            @PathVariable Long doctorId,
            @PathVariable Long scheduleId) {

        return ResponseEntity.ok(ApiResponse.success(
                "Doctor schedule fetched successfully",
                service.getSchedule(
                        doctorId,
                        scheduleId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorScheduleResponse>>> searchSchedules(
            @PathVariable Long doctorId,
            DoctorScheduleSearchRequest request,
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.success(
                "Doctor schedule fetched successfully",
                service.searchSchedules(
                        doctorId,
                        request,
                        pageable)));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(
            @PathVariable Long doctorId,
            @PathVariable Long scheduleId) {

        service.deleteSchedule(
                doctorId,
                scheduleId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor schedule deleted successfully",null));
    }
}