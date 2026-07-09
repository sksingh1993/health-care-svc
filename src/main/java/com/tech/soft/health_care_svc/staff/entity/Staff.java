package com.tech.soft.health_care_svc.staff.entity;

import com.tech.soft.health_care_svc.auth.entity.User;
import com.tech.soft.health_care_svc.auth.enums.RoleType;
import com.tech.soft.health_care_svc.common.entity.BaseEntity;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.doctor.enums.Specialization;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import com.tech.soft.health_care_svc.staff.enums.Department;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = true, unique = true, length = 20)
    private String employeeCode;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 15)
    private String mobile;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType designation;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}