package com.tech.soft.health_care_svc.common.exception;

import java.util.Map;

public class DuplicateResourceException extends RuntimeException {

    private final Map<String, String> validationErrors;

    public DuplicateResourceException(
            Map<String, String> validationErrors) {

        super("Validation Failed");
        this.validationErrors = validationErrors;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}