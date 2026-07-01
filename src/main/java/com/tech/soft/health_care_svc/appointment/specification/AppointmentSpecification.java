package com.tech.soft.health_care_svc.appointment.specification;


import com.tech.soft.health_care_svc.appointment.entity.Appointment;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class AppointmentSpecification {

    private AppointmentSpecification() {
    }

    public static Specification<Appointment> active() {

        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

    public static Specification<Appointment> patient(Long patientId) {

        return (root, query, cb) -> {

            if (patientId == null)
                return null;

            return cb.equal(
                    root.get("patient").get("id"),
                    patientId);
        };
    }

    public static Specification<Appointment> doctor(Long doctorId) {

        return (root, query, cb) -> {

            if (doctorId == null)
                return null;

            return cb.equal(
                    root.get("doctor").get("id"),
                    doctorId);
        };
    }

    public static Specification<Appointment> status(
            AppointmentStatus status) {

        return (root, query, cb) -> {

            if (status == null)
                return null;

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Appointment> fromDate(
            LocalDate fromDate) {

        return (root, query, cb) -> {

            if (fromDate == null)
                return null;

            return cb.greaterThanOrEqualTo(
                    root.get("appointmentDate"),
                    fromDate);
        };
    }

    public static Specification<Appointment> toDate(
            LocalDate toDate) {

        return (root, query, cb) -> {

            if (toDate == null)
                return null;

            return cb.lessThanOrEqualTo(
                    root.get("appointmentDate"),
                    toDate);
        };
    }
}