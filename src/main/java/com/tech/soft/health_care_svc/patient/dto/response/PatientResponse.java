package com.tech.soft.health_care_svc.patient.dto.response;
import com.tech.soft.health_care_svc.patient.enums.BloodGroup;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Long id;

    private String patientCode;

    private String firstName;

    private String lastName;

    private String fullName;

    private Integer age;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String mobile;

    private String email;

    private BloodGroup bloodGroup;

    private String allergies;

    private String address;

    private Boolean active;

    private String emergencyContactName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid emergency contact number")
    private String emergencyContactNumber;
}