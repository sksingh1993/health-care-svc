package com.tech.soft.health_care_svc.staff.controller;

import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import com.tech.soft.health_care_svc.doctor.dto.response.DoctorResponse;
import com.tech.soft.health_care_svc.staff.dto.request.CreateStaffRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffSearchRequest;
import com.tech.soft.health_care_svc.staff.dto.request.StaffUpdateRequest;
import com.tech.soft.health_care_svc.staff.dto.response.StaffResponse;
import com.tech.soft.health_care_svc.staff.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
//@Tag(name = "Staff", description = "Staff Management APIs")
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StaffResponse>> create(
            @Valid @RequestBody CreateStaffRequest request) {

        StaffResponse response = staffService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        response.getDesignation()+" created successfully",
                        response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StaffResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody StaffUpdateRequest request) {
        StaffResponse response = staffService.updateStaff(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        response.getDesignation()+" created successfully",
                        response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StaffResponse>> getById(
            @PathVariable Long id) {
        StaffResponse response = staffService.getStaff(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        response.getDepartment()+" fetched successfully",
                        response));
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<StaffResponse>>>  search(
            @RequestBody StaffSearchRequest request,
            Pageable pageable) {
        Page<StaffResponse> response = staffService.searchStaff(request, pageable);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Staff fetched successfully",
                        response));
    }

    /*@GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StaffResponse>> getAll() {

        return ResponseEntity.ok(
                staffService.getAll());
    }*/

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        staffService.deleteStaff(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Staff deleted successfully",
                        null));
    }
}