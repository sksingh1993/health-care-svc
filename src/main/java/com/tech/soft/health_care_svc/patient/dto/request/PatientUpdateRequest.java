package com.tech.soft.health_care_svc.patient.dto.request;

import com.tech.soft.health_care_svc.patient.enums.BloodGroup;
import com.tech.soft.health_care_svc.patient.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequest {

   @NotBlank(message = "First name is mandatory")
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "DOB must be in the past")
    private LocalDate dateOfBirth;

   @NotBlank(message = "Mobile number is mandatory")
   @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number")
    private String mobile;

    //@Email
    private String email;

    private BloodGroup bloodGroup;

    private String allergies;

    private String address;

    private String emergencyContactName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid emergency contact number")
    private String emergencyContactNumber;
}