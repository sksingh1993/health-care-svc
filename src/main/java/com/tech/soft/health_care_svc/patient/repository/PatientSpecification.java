package com.tech.soft.health_care_svc.patient.repository;

import com.tech.soft.health_care_svc.patient.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

public final class PatientSpecification {

    private PatientSpecification() {
    }

    public static Specification<Patient> hasName(String name) {

        return (root, query, cb) -> {

            if (name == null || name.isBlank()) {
                return null;
            }

            return cb.or(

                    cb.like(
                            cb.lower(root.get("firstName")),
                            "%" + name.toLowerCase() + "%"),

                    cb.like(
                            cb.lower(root.get("lastName")),
                            "%" + name.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Patient> hasMobile(String mobile) {

        return (root, query, cb) -> {

            if (mobile == null || mobile.isBlank()) {
                return null;
            }

            return cb.equal(root.get("mobile"), mobile);
        };
    }

    public static Specification<Patient> hasPatientCode(String patientCode) {

        return (root, query, cb) -> {

            if (patientCode == null || patientCode.isBlank()) {
                return null;
            }

            return cb.equal(root.get("patientCode"), patientCode);
        };
    }

    public static Specification<Patient> isActive() {

        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

}