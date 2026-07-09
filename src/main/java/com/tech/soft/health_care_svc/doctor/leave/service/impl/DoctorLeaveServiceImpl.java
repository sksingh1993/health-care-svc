package com.tech.soft.health_care_svc.doctor.leave.service.impl;


import com.tech.soft.health_care_svc.common.util.DateUtils;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveSearchRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.response.DoctorLeaveResponse;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveStatus;
import com.tech.soft.health_care_svc.doctor.leave.mapper.DoctorLeaveMapper;
import com.tech.soft.health_care_svc.doctor.leave.repository.DoctorLeaveRepository;
import com.tech.soft.health_care_svc.doctor.leave.service.DoctorLeaveService;
import com.tech.soft.health_care_svc.doctor.leave.specification.DoctorLeaveSpecification;
import com.tech.soft.health_care_svc.doctor.leave.validator.DoctorLeaveValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorLeaveServiceImpl implements DoctorLeaveService {

    private final DoctorLeaveRepository repository;

    private final DoctorLeaveMapper mapper;

    private final DoctorLeaveValidator validator;

    @Override
    public DoctorLeaveResponse createLeave(Long doctorId, DoctorLeaveRequest request) {

        Doctor doctor = validator.validateCreate(doctorId, request);

        DoctorLeave leave = mapper.toEntity(request);

        leave.setLeaveStatus(LeaveStatus.PENDING);

        leave.setNumberOfDays(DateUtils.calculateDays( request.getFromDate(), request.getToDate()));

        leave.setDoctor(doctor);

        leave = repository.save(leave);

        return mapper.toResponse(leave);
    }

    @Override
    public DoctorLeaveResponse updateLeave(Long doctorId, Long id, DoctorLeaveUpdateRequest request) {

        DoctorLeave leave = validator.validateLeave(doctorId, id);

        Doctor doctor = validator.validateUpdate(doctorId, id, request);

        mapper.updateEntity(request, leave);

        leave.setNumberOfDays(DateUtils.calculateDays( request.getFromDate(), request.getToDate()));

        leave.setDoctor(doctor);

        leave = repository.save(leave);

        return mapper.toResponse(leave);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorLeaveResponse getLeave(Long doctorId, Long id) {

        DoctorLeave leave = validator.validateLeave(doctorId, id);

        return mapper.toResponse(leave);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorLeaveResponse> searchLeaves(Long doctorId, DoctorLeaveSearchRequest request, Pageable pageable) {

        Specification<DoctorLeave> specification = Specification.where(DoctorLeaveSpecification.active()).and(DoctorLeaveSpecification.doctor(doctorId)).and(DoctorLeaveSpecification.leaveType(request.getLeaveType())).and(DoctorLeaveSpecification.fromDate(request.getFromDate())).and(DoctorLeaveSpecification.toDate(request.getToDate()));

        return repository.findAll(specification, pageable).map(mapper::toResponse);
    }

    @Override
    public void deleteLeave(Long doctorId, Long id) {

        DoctorLeave leave = validator.validateLeave(doctorId, id);

        leave.setActive(false);

        repository.save(leave);
    }
}