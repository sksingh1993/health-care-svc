CREATE TABLE appointment (

                             id BIGINT AUTO_INCREMENT PRIMARY KEY,

                             appointment_code VARCHAR(20) NOT NULL UNIQUE,

                             patient_id BIGINT NOT NULL,

                             doctor_id BIGINT NOT NULL,

                             appointment_date DATE NOT NULL,

                             appointment_time TIME NOT NULL,

                             appointment_end_time TIME NOT NULL,

                             status VARCHAR(30) NOT NULL,

                             appointment_type VARCHAR(30) NOT NULL,

                             reason VARCHAR(500),

                             remarks TEXT,

                             active BOOLEAN DEFAULT TRUE,

                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                 ON UPDATE CURRENT_TIMESTAMP,

                             CONSTRAINT fk_appointment_patient
                                 FOREIGN KEY(patient_id)
                                     REFERENCES patient(id),

                             CONSTRAINT fk_appointment_doctor
                                 FOREIGN KEY(doctor_id)
                                     REFERENCES doctor(id)
);

CREATE INDEX idx_appointment_doctor_date
    ON appointment(doctor_id, appointment_date);

CREATE INDEX idx_appointment_patient_date
    ON appointment(patient_id, appointment_date);