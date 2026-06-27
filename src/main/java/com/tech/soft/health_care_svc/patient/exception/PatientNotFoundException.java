package com.tech.soft.health_care_svc.patient.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Long patientId) {
        super("Patient not found with id : " + patientId);
    }

    public PatientNotFoundException(String message) {
        super(message);
    }
}