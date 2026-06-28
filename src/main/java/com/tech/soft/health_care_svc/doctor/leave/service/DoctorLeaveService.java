package com.tech.soft.health_care_svc.doctor.leave.service;


import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveSearchRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.response.DoctorLeaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorLeaveService {

    DoctorLeaveResponse createLeave(Long doctorId,DoctorLeaveRequest request);

    DoctorLeaveResponse updateLeave(Long doctorId,Long id, DoctorLeaveUpdateRequest request);

    DoctorLeaveResponse getLeave(Long doctorId,Long id);

    Page<DoctorLeaveResponse> searchLeaves(Long doctorId,DoctorLeaveSearchRequest request, Pageable pageable);

    void deleteLeave(Long doctorId,Long id);
}