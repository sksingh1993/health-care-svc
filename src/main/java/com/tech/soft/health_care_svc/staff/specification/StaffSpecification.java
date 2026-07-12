package com.tech.soft.health_care_svc.staff.specification;


import com.tech.soft.health_care_svc.staff.enums.Department;
import com.tech.soft.health_care_svc.staff.entity.Staff;
import org.springframework.data.jpa.domain.Specification;

public final class StaffSpecification {

    private StaffSpecification() {
    }

    public static Specification<Staff> isActive() {
        return (root, query, cb) ->
                cb.isTrue(root.get("active"));
    }

    public static Specification<Staff> hasEmployeeId(String employeeId) {

        return (root, query, cb) -> {

            if (employeeId == null || employeeId.isBlank()) {
                return null;
            }

            return cb.equal(root.get("employeeId"), employeeId);
        };
    }

    public static Specification<Staff> hasMobile(String mobile) {

        return (root, query, cb) -> {

            if (mobile == null || mobile.isBlank()) {
                return null;
            }

            return cb.equal(root.get("mobile"), mobile);
        };
    }

    public static Specification<Staff> hasName(String name) {

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

    public static Specification<Staff> hasDepartment(
            Department department) {

        return (root, query, cb) -> {

            if (department == null) {
                return null;
            }

            return cb.equal(root.get("department"), department);
        };
    }
}