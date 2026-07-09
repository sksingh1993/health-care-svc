package com.tech.soft.health_care_svc.appointment.service;


import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentSearchRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentUpdateRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AppointmentService  {
    AppointmentResponse createAppointment(AppointmentRequest request) ;


    AppointmentResponse updateAppointment(
            Long id,
            AppointmentUpdateRequest request) ;

    AppointmentResponse getAppointment(Long id) ;

    Page<AppointmentResponse> searchAppointments(
            AppointmentSearchRequest request,
            Pageable pageable) ;


    void cancelAppointment(Long id) ;


    AppointmentResponse checkIn(Long id) ;


    AppointmentResponse complete(Long id) ;
}