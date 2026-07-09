package com.tech.soft.health_care_svc.doctor.leave.specification;


import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class DoctorLeaveSpecification {

    private DoctorLeaveSpecification() {
    }

    public static Specification<DoctorLeave> active() {

        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

    public static Specification<DoctorLeave> doctor(Long doctorId) {

        return (root, query, cb) -> {

            if (doctorId == null) {
                return null;
            }

            return cb.equal(
                    root.get("doctor").get("id"),
                    doctorId);
        };
    }

    public static Specification<DoctorLeave> leaveType(
            LeaveType leaveType) {

        return (root, query, cb) -> {

            if (leaveType == null) {
                return null;
            }

            return cb.equal(
                    root.get("leaveType"),
                    leaveType);
        };
    }

    public static Specification<DoctorLeave> fromDate(
            LocalDate fromDate) {

        return (root, query, cb) -> {

            if (fromDate == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(
                    root.get("leaveDate"),
                    fromDate);
        };
    }

    public static Specification<DoctorLeave> toDate(
            LocalDate toDate) {

        return (root, query, cb) -> {

            if (toDate == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(
                    root.get("leaveDate"),
                    toDate);
        };
    }
}