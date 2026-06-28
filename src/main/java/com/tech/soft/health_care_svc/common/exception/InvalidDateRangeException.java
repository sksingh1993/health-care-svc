package com.tech.soft.health_care_svc.common.exception;

import org.apache.coyote.BadRequestException;

public class InvalidDateRangeException extends RuntimeException{


    public InvalidDateRangeException(String message) {
        super(message);
    }
}
