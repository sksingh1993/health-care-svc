package com.tech.soft.health_care_svc.doctor.service;


import com.tech.soft.health_care_svc.doctor.dto.request.DoctorRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorSearchRequest;
import com.tech.soft.health_care_svc.doctor.dto.request.DoctorUpdateRequest;
import com.tech.soft.health_care_svc.doctor.dto.response.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorRequest request);

    DoctorResponse getDoctor(Long id);

    Page<DoctorResponse> searchDoctors(
            DoctorSearchRequest request,
            Pageable pageable);

    DoctorResponse updateDoctor(
            Long id,
            DoctorUpdateRequest request);

    void deleteDoctor(Long id);
}