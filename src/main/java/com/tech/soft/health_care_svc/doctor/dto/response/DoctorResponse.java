package com.tech.soft.health_care_svc.doctor.dto.response;


import com.tech.soft.health_care_svc.patient.enums.Gender;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;

    private String doctorCode;

    private String firstName;

    private String lastName;

    private String fullName;

    private Integer age;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String mobile;

    private String email;

    private String specialization;

    private String qualification;

    private Integer experienceYears;

    private BigDecimal consultationFee;

    private String registrationNumber;

    private Boolean active;

    private String address;
}