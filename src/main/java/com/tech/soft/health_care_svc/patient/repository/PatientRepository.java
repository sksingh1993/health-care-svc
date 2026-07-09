package com.tech.soft.health_care_svc.patient.repository;


import com.tech.soft.health_care_svc.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PatientRepository
        extends JpaRepository<Patient, Long>,
                JpaSpecificationExecutor<Patient> {

    Optional<Patient> findByPatientCode(String patientCode);

    Optional<Patient> findByMobile(String mobile);

    boolean existsByMobile(String mobile);

    boolean existsByPatientCode(String patientCode);

}