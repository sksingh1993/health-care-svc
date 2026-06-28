package com.tech.soft.health_care_svc.doctor.controller;


import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorSearchRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorUpdateRequest;
import com.tech.soft.health_care_svc.doctor.dto.response.DoctorResponse;
import com.tech.soft.health_care_svc.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorRequest request) {

        DoctorResponse response = doctorService.createDoctor(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor created successfully",
                        response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctor(
            @PathVariable Long id) {

        DoctorResponse response = doctorService.getDoctor(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor fetched successfully",
                        response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorResponse>>> searchDoctors(
            @ModelAttribute DoctorSearchRequest request,
            Pageable pageable) {

        Page<DoctorResponse> response =
                doctorService.searchDoctors(request, pageable);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctors fetched successfully",
                        response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateRequest request) {

        DoctorResponse response =
                doctorService.updateDoctor(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor updated successfully",
                        response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor deleted successfully",
                        null));
    }
}