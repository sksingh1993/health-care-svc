package com.tech.soft.health_care_svc.doctor.entity;


import com.tech.soft.health_care_svc.common.entity.BaseEntity;
import com.tech.soft.health_care_svc.doctor.enums.Specialization;
import com.tech.soft.health_care_svc.doctor.leave.entity.DoctorLeave;
import com.tech.soft.health_care_svc.doctor.schedule.entity.DoctorSchedule;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_code", nullable = true, unique = true)
    private String doctorCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialization specialization;

    private String qualification;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "consultation_fee", precision = 10, scale = 2, nullable = false)
    private BigDecimal consultationFee;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorLeave> doctorLeaves = new ArrayList<>();
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorSchedule> doctorSchedules = new ArrayList<>();
}