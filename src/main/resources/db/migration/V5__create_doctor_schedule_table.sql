CREATE TABLE doctor_schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    doctor_id BIGINT NOT NULL,

    day_of_week VARCHAR(20) NOT NULL,

    start_time TIME NOT NULL,

    end_time TIME NOT NULL,

    slot_duration INT NOT NULL,

    consultation_limit INT NOT NULL,

    active BOOLEAN DEFAULT TRUE,

    created_at DATETIME,

    updated_at DATETIME,

    created_by BIGINT,

    updated_by BIGINT,

    version BIGINT DEFAULT 0,

    CONSTRAINT fk_doctor_schedule_doctor
        FOREIGN KEY (doctor_id)
            REFERENCES doctor(id)
);
CREATE INDEX idx_schedule_doctor
    ON doctor_schedule(doctor_id);

CREATE INDEX idx_schedule_day
    ON doctor_schedule(day_of_week);
