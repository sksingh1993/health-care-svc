package com.tech.soft.health_care_svc.doctor.leave.controller;


import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveSearchRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.response.DoctorLeaveResponse;
import com.tech.soft.health_care_svc.doctor.leave.service.DoctorLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorLeaveController {

    private final DoctorLeaveService service;

    @PostMapping("/doctors/{doctorId}/leaves")
    public ResponseEntity<ApiResponse<DoctorLeaveResponse>> create(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorLeaveRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor leave created successfully",
                        service.createLeave(doctorId,request)));
    }

    @PutMapping("/doctors/{doctorId}/leaves/{id}")
    public ResponseEntity<ApiResponse<DoctorLeaveResponse>> update(
            @PathVariable Long doctorId,
            @PathVariable Long id,
            @Valid @RequestBody DoctorLeaveUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor leave updated successfully",
                        service.updateLeave(doctorId,id, request)));
    }

    @GetMapping("/doctors/{doctorId}/leaves/{id}")
    public ResponseEntity<ApiResponse<DoctorLeaveResponse>> get(
            @PathVariable Long doctorId,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor leave fetched successfully",
                        service.getLeave(doctorId,id)));
    }

    @GetMapping("/doctors/{doctorId}/leaves")
    public ResponseEntity<ApiResponse<Page<DoctorLeaveResponse>>> search(
            @PathVariable Long doctorId,
            @ModelAttribute DoctorLeaveSearchRequest request,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor leaves fetched successfully",
                        service.searchLeaves(doctorId,request, pageable)));
    }
    @GetMapping("/leaves")
    public ResponseEntity<ApiResponse<Page<DoctorLeaveResponse>>> search(
            @ModelAttribute DoctorLeaveSearchRequest request,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor leaves fetched successfully",
                        service.searchLeaves(request, pageable)));
    }

    @DeleteMapping("/doctors/{doctorId}/leaves/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long doctorId,
            @PathVariable Long id) {

        service.deleteLeave(doctorId,id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor leave deleted successfully",
                        null));
    }
}