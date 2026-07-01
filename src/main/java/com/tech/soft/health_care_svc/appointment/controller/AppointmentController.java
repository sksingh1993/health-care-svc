package com.tech.soft.health_care_svc.appointment.controller;


import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentSearchRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentUpdateRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AppointmentResponse;
import com.tech.soft.health_care_svc.appointment.service.AppointmentService;
import com.tech.soft.health_care_svc.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> create(
            @Valid @RequestBody AppointmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Appointment booked successfully",
                        service.createAppointment(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment updated successfully",
                        service.updateAppointment(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment fetched successfully",
                        service.getAppointment(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppointmentResponse>>> search(
            @ModelAttribute AppointmentSearchRequest request,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments fetched successfully",
                        service.searchAppointments(request, pageable)));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(
            @PathVariable Long id) {

        service.cancelAppointment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment cancelled successfully",
                        null));
    }

    @PutMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<AppointmentResponse>> checkIn(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient checked in successfully",
                        service.checkIn(id)));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> complete(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment completed successfully",
                        service.complete(id)));
    }
}