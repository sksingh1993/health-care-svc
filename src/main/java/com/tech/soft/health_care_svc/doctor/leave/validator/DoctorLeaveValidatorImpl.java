package com.tech.soft.health_care_svc.doctor.leave.validator;


import com.tech.soft.health_care_svc.common.exception.DuplicateResourceException;
import com.tech.soft.health_care_svc.common.exception.InvalidDateRangeException;
import com.tech.soft.health_care_svc.common.exception.ResourceNotFoundException;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveRequest;
import com.tech.soft.health_care_svc.doctor.leave.dto.request.DoctorLeaveUpdateRequest;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import com.tech.soft.health_care_svc.doctor.leave.repository.DoctorLeaveRepository;
import com.tech.soft.health_care_svc.doctor.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DoctorLeaveValidatorImpl
        implements DoctorLeaveValidator {

    private final DoctorValidator doctorValidator;

    private final DoctorLeaveRepository doctorLeaveRepository;

    @Override
    public Doctor validateCreate(Long doctorId, DoctorLeaveRequest request) {

        Doctor doctor = doctorValidator.validateActiveDoctor(
                doctorId);

        if (request.getFromDate().isAfter(request.getToDate())) {
            throw new IllegalArgumentException(
                    "From date cannot be after To date");
        }
        if (doctorLeaveRepository
                .existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndActiveTrue(
                        doctorId,
                        request.getToDate(),
                        request.getFromDate())) {

            throw new DuplicateResourceException(
                    "Doctor already has leave during the selected period.");
        }

        return doctor;
    }

    @Override
    public Doctor validateUpdate(
            Long doctorId,
            Long id,
            DoctorLeaveUpdateRequest request) {

        validateLeave(doctorId, id);

        if(request.getFromDate().isAfter(request.getToDate())){
            throw new InvalidDateRangeException(
                    "From date cannot be after To date");
        }

        Doctor doctor = doctorValidator.validateActiveDoctor(
                doctorId);

        if (doctorLeaveRepository
                .existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndIdNotAndActiveTrue(
                        doctorId,
                        request.getToDate(),
                        request.getFromDate(),
                        id)) {

            throw new DuplicateResourceException(
                    "Doctor already has leave during the selected period.");
        }
        return doctor;
    }

    @Override
    public DoctorLeave validateLeave(Long doctorId, Long id) {

        DoctorLeave doctorLeave = doctorLeaveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor Leave",
                                id));
        if (!doctorLeave.getDoctor().getId().equals(doctorId)) {
            throw new ResourceNotFoundException(String.format("Doctor leave id : %d not belong to doctor : %s", id, doctorLeave.getDoctor().getFirstName()));
        }
        return doctorLeave;
    }

    @Override
    public void validateDoctorAvailability(
            Long doctorId,
            LocalDate fromDate,
            LocalDate toDate) {

        if (doctorLeaveRepository
                .existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndActiveTrue(
                        doctorId,
                        fromDate,
                        fromDate)) {

            throw new DuplicateResourceException(
                    "Doctor already has leave during the selected period.");
        }
    }
}