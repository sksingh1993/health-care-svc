package com.tech.soft.health_care_svc.patient.controller;


import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import com.tech.soft.health_care_svc.patient.dto.request.PatientRequest;
import com.tech.soft.health_care_svc.patient.dto.request.PatientUpdateRequest;
import com.tech.soft.health_care_svc.patient.dto.response.PatientResponse;
import com.tech.soft.health_care_svc.patient.dto.search.PatientSearchRequest;
import com.tech.soft.health_care_svc.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientRequest request) {

        PatientResponse response = patientService.createPatient(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Patient created successfully",
                        response));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatient(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient fetched successfully",
                        patientService.getPatient(id)));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient updated successfully",
                        patientService.updatePatient(id, request)));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<Void>> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient deleted successfully",
                        null));
    }

    @GetMapping
   // @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> searchPatients(
            PatientSearchRequest request,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patients fetched successfully",
                        patientService.searchPatients(request, pageable)));
    }

}