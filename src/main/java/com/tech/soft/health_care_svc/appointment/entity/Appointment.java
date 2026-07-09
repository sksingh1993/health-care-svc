package com.tech.soft.health_care_svc.appointment.entity;

import com.tech.soft.health_care_svc.appointment.enums.AppointmentStatus;
import com.tech.soft.health_care_svc.appointment.enums.AppointmentType;
import com.tech.soft.health_care_svc.common.entity.BaseEntity;
import com.tech.soft.health_care_svc.common.util.CodeGenerator;
import com.tech.soft.health_care_svc.doctor.entity.Doctor;
import com.tech.soft.health_care_svc.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
//@Table(name = "appointment")
@Table(
        name = "appointment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_patient_date_time",
                        columnNames = {"doctor_id","patient_id", "appointmentDate", "appointmentTime"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_code", nullable = true, unique = true)
    private String appointmentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Column(nullable = false)
    private LocalTime appointmentEndTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentType appointmentType;

    @Column(length = 500)
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

   /* @PostPersist
    public void generateAppointmentCode() {
        String year = String.valueOf(LocalDate.now().getYear());
        this.appointmentCode = CodeGenerator.generate("APT",this.getId());
    }*/
}