CREATE TABLE patient(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_code VARCHAR(20)  UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(100),
    blood_group VARCHAR(10),
    allergies TEXT,
    address TEXT,
    emergency_contact_name VARCHAR(100),
    emergency_contact_number VARCHAR(15),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by BIGINT,
    updated_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);


CREATE INDEX idx_patient_mobile
    ON patient(mobile);

CREATE INDEX idx_patient_code
    ON patient(patient_code);

CREATE INDEX idx_patient_name
    ON patient(first_name, last_name);