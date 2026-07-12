package com.tech.soft.health_care_svc.doctor.schedule.specification;


import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.doctor.schedule.enums.DayOfWeek;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DoctorScheduleSpecification {

    private DoctorScheduleSpecification() {
    }

    public static Specification<DoctorSchedule> active() {
        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }
    public static Specification<DoctorSchedule> doctorName(String doctorName) {

        return (root, query, cb) -> {

            if (!StringUtils.hasText(doctorName)) {
                return null;
            }

            Join<Object, Object> doctor = root.join("doctor");

            String search = "%" + doctorName.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(doctor.get("firstName")), search),
                    cb.like(cb.lower(doctor.get("lastName")), search)
            );
        };
    }

    public static Specification<DoctorSchedule> doctor(Long doctorId) {

        if (doctorId == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.equal(root.get("doctor").get("id"), doctorId);
    }

    public static Specification<DoctorSchedule> dayOfWeek(DayOfWeek dayOfWeek) {
        return (root, query, cb) -> {

            if (dayOfWeek == null) {
                return null;
            }

            return cb.equal(root.get("dayOfWeek"), dayOfWeek);
        };

    }

    public static Specification<DoctorSchedule> slotDuration(Integer slotDuration) {

        if (slotDuration == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.equal(root.get("slotDuration"), slotDuration);
    }
}