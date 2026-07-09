package com.tech.soft.health_care_svc.doctor.leave.repository;


import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorLeaveRepository extends
        JpaRepository<DoctorLeave, Long>,
        JpaSpecificationExecutor<DoctorLeave> {

    Optional<DoctorLeave> findByIdAndActiveTrue(Long id);


    boolean existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndActiveTrue(
            Long doctorId,
            LocalDate toDate,
            LocalDate fromDate
    );

    boolean existsByDoctorIdAndFromDateLessThanEqualAndToDateGreaterThanEqualAndIdNotAndActiveTrue(
            Long doctorId,
            LocalDate toDate,
            LocalDate fromDate,
            Long id
    );

    List<DoctorLeave> findByDoctorIdAndActiveTrue(Long doctorId);

    /*Optional<DoctorLeave> findByDoctorIdAndLeaveDateAndActiveTrue(
            Long doctorId,
            LocalDate leaveDate);*/
}