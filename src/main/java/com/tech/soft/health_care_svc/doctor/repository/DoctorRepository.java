package com.tech.soft.health_care_svc.doctor.repository;


import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DoctorRepository extends
        JpaRepository<Doctor, Long>,
        JpaSpecificationExecutor<Doctor> {

    Optional<Doctor> findByDoctorCode(String doctorCode);

    Optional<Doctor> findByMobile(String mobile);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByRegistrationNumber(String registrationNumber);

    boolean existsByDoctorCode(String doctorCode);

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByRegistrationNumber(String registrationNumber);

}