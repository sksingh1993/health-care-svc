package com.tech.soft.health_care_svc.patient.exception;

public class DuplicateMobileException extends RuntimeException {

    public DuplicateMobileException(String message) {
        super(message);
    }
}