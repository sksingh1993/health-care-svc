package com.tech.soft.health_care_svc.doctor.specification;


import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.enums.Specialization;
import org.springframework.data.jpa.domain.Specification;

public final class DoctorSpecification {

    private DoctorSpecification() {
    }

    public static Specification<Doctor> isActive() {
        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

    public static Specification<Doctor> hasDoctorCode(String doctorCode) {

        return (root, query, cb) -> {

            if (doctorCode == null || doctorCode.isBlank()) {
                return null;
            }

            return cb.equal(root.get("doctorCode"), doctorCode);
        };
    }

    public static Specification<Doctor> hasMobile(String mobile) {

        return (root, query, cb) -> {

            if (mobile == null || mobile.isBlank()) {
                return null;
            }

            return cb.equal(root.get("mobile"), mobile);
        };
    }

    public static Specification<Doctor> hasName(String name) {

        return (root, query, cb) -> {

            if (name == null || name.isBlank()) {
                return null;
            }

            String search = "%" + name.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), search),
                    cb.like(cb.lower(root.get("lastName")), search)
            );
        };
    }

    public static Specification<Doctor> hasSpecialization(
            Specialization specialization) {

        return (root, query, cb) -> {

            if (specialization == null) {
                return null;
            }

            return cb.equal(root.get("specialization"), specialization);
        };
    }
}