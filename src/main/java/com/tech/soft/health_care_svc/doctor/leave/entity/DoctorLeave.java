package com.tech.soft.health_care_svc.doctor.leave.entity;


import com.tech.soft.health_care_svc.common.entity.BaseEntity;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveStatus;
import com.tech.soft.health_care_svc.doctor.leave.enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctor_leave")
public class DoctorLeave extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    private Integer numberOfDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus leaveStatus;

    @Column(length = 500)
    private String reason;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}