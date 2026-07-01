package com.tech.soft.health_care_svc.appointment.service.impl;


import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentSearchRequest;
import com.tech.soft.health_care_svc.appointment.dto.request.AppointmentUpdateRequest;
import com.tech.soft.health_care_svc.appointment.dto.response.AppointmentResponse;
import com.tech.soft.health_care_svc.appointment.entity.Appointment;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.appointment.mapper.AppointmentMapper;
import com.tech.soft.health_care_svc.appointment.repository.AppointmentRepository;
import com.tech.soft.health_care_svc.appointment.service.AppointmentService;
import com.tech.soft.health_care_svc.appointment.specification.AppointmentSpecification;
import com.tech.soft.health_care_svc.appointment.validator.AppointmentValidationResult;
import com.tech.soft.health_care_svc.appointment.validator.AppointmentValidator;
import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.common.util.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    private final AppointmentMapper mapper;

    private final AppointmentValidator validator;

    //private final CodeGeneratorService codeGeneratorService;


    public AppointmentResponse createAppointment(AppointmentRequest request) {

        AppointmentValidationResult validation =
                validator.validateCreate(request);

        Appointment appointment = mapper.toEntity(request);

        appointment.setPatient(validation.getPatient());

        appointment.setDoctor(validation.getDoctor());

        appointment.setAppointmentEndTime(
                validation.getAppointmentEndTime());

//        appointment = repository.save(appointment);
//
//        appointment.setAppointmentCode(
//                CodeGenerator.generate("APT",appointment.getId()));

//        appointment = repository.save(appointment);
        try {
            appointment = repository.save(appointment);

            appointment.setAppointmentCode(
                    CodeGenerator.generate("APT",appointment.getId()));
            appointment = repository.save(appointment);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException(
                    "Appointment already exists for this doctor, patient, date and time.");
        }

        return mapper.toResponse(appointment);
    }


    public AppointmentResponse updateAppointment(
            Long id,
            AppointmentUpdateRequest request) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment",
                                id));

        mapper.updateEntity(request, appointment);

        appointment = repository.save(appointment);

        return mapper.toResponse(appointment);
    }


    @Transactional(readOnly = true)
    public AppointmentResponse getAppointment(Long id) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment",
                                id));

        return mapper.toResponse(appointment);
    }


    @Transactional(readOnly = true)
    public Page<AppointmentResponse> searchAppointments(
            AppointmentSearchRequest request,
            Pageable pageable) {

        Specification<Appointment> specification =
                Specification.where(AppointmentSpecification.active())
                        .and(AppointmentSpecification.patient(request.getPatientId()))
                        .and(AppointmentSpecification.doctor(request.getDoctorId()))
                        .and(AppointmentSpecification.status(request.getStatus()))
                        .and(AppointmentSpecification.fromDate(request.getFromDate()))
                        .and(AppointmentSpecification.toDate(request.getToDate()));

        return repository.findAll(specification, pageable)
                .map(mapper::toResponse);
    }


    public void cancelAppointment(Long id) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment",
                                id));

        appointment.setStatus(AppointmentStatus.CANCELLED);

        repository.save(appointment);
    }


    public AppointmentResponse checkIn(Long id) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment",
                                id));

        appointment.setStatus(AppointmentStatus.CHECKED_IN);

        return mapper.toResponse(repository.save(appointment));
    }


    public AppointmentResponse complete(Long id) {

        Appointment appointment = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment",
                                id));

        appointment.setStatus(AppointmentStatus.BILLED);

        return mapper.toResponse(repository.save(appointment));
    }
}