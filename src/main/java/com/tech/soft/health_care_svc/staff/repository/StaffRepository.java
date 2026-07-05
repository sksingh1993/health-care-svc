package com.tech.soft.health_care_svc.staff.repository;


import com.tech.soft.health_care_svc.staff.entity.Staff;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long>,
        JpaSpecificationExecutor<Staff> {

    Optional<Staff> findByUserId(Long userId);

    Optional<Staff> findByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    Optional<Staff> findByEmail(String email);

    Optional<Staff> findByMobile(String mobile);
}