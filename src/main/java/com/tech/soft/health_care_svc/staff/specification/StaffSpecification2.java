package com.tech.soft.health_care_svc.staff.specification;

import com.tech.soft.health_care_svc.staff.dto.request.StaffSearchRequest;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class StaffSpecification2 {

    public static Specification<Staff> search(StaffSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(request.getEmployeeCode())) {
                predicates.add(
                        cb.like(
                                cb.upper(root.get("employeeCode")),
                                "%" + request.getEmployeeCode().toUpperCase() + "%"));
            }

            if (StringUtils.hasText(request.getFirstName())) {
                predicates.add(
                        cb.like(
                                cb.upper(root.get("firstName")),
                                "%" + request.getFirstName().toUpperCase() + "%"));
            }

            if (StringUtils.hasText(request.getLastName())) {
                predicates.add(
                        cb.like(
                                cb.upper(root.get("lastName")),
                                "%" + request.getLastName().toUpperCase() + "%"));
            }

            if (StringUtils.hasText(request.getMobile())) {
                predicates.add(
                        cb.equal(root.get("mobile"), request.getMobile()));
            }

            if (request.getDepartment() != null) {
                predicates.add(
                        cb.equal(root.get("department"), request.getDepartment()));
            }

            if (request.getActive() != null) {
                predicates.add(
                        cb.equal(root.get("active"), request.getActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}