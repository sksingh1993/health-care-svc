package com.tech.soft.health_care_svc.doctor.schedule.service;

import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleSearchRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.request.DoctorScheduleUpdateRequest;
import com.tech.soft.health_care_svc.doctor.schedule.dto.response.DoctorScheduleResponse;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.doctor.schedule.mapper.DoctorScheduleMapper;
import com.tech.soft.health_care_svc.doctor.schedule.repository.DoctorScheduleRepository;
import com.tech.soft.health_care_svc.doctor.schedule.specification.DoctorScheduleSpecification;
import com.tech.soft.health_care_svc.doctor.schedule.validator.DoctorScheduleValidator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorScheduleServiceImpl
        implements DoctorScheduleService {

    private final DoctorScheduleRepository repository;

    private final DoctorScheduleMapper mapper;

    private final DoctorScheduleValidator validator;

    @Override
    public DoctorScheduleResponse createSchedule(
            Long doctorId,
            DoctorScheduleRequest request) {

        Doctor doctor = validator.validateCreate(
                doctorId,
                request);

        DoctorSchedule schedule = mapper.toEntity(request);

        schedule.setDoctor(doctor);

        schedule = repository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    public DoctorScheduleResponse updateSchedule(
            Long doctorId,
            Long scheduleId,
            DoctorScheduleUpdateRequest request){

        DoctorSchedule schedule =
                validator.validateSchedule(
                        doctorId,
                        scheduleId);

        Doctor doctor =
                validator.validateUpdate(
                        doctorId,
                        scheduleId,
                        request);

        mapper.updateEntity(request, schedule);

        schedule.setDoctor(doctor);

        schedule = repository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorScheduleResponse getSchedule(
            Long doctorId,
            Long scheduleId) {

        DoctorSchedule schedule =
                validator.validateSchedule(
                        doctorId,
                        scheduleId);

        return mapper.toResponse(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorScheduleResponse> searchSchedules(
            Long doctorId,
            DoctorScheduleSearchRequest request,
            Pageable pageable) {

        Specification<DoctorSchedule> specification =
                Specification.where(
                                DoctorScheduleSpecification.active())
                        .and(DoctorScheduleSpecification.doctor(doctorId))
                        .and(DoctorScheduleSpecification.dayOfWeek(
                                request.getDayOfWeek()))
                        .and(DoctorScheduleSpecification.slotDuration(
                                request.getSlotDuration()));

        return repository.findAll(specification, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void deleteSchedule(
            Long doctorId,
            Long scheduleId) {

        DoctorSchedule schedule =
                validator.validateSchedule(
                        doctorId,
                        scheduleId);

        schedule.setActive(false);

        repository.save(schedule);
    }
}