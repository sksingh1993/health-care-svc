package com.tech.soft.health_care_svc.auth.repository;

import com.tech.soft.health_care_svc.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleCode(String roleCode);
}