CREATE TABLE notification
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    notification_code VARCHAR(50) NOT NULL UNIQUE,

    patient_id BIGINT,

    notification_type VARCHAR(30) NOT NULL,

    recipient VARCHAR(255) NOT NULL,

    subject VARCHAR(255),

    message TEXT NOT NULL,

    notification_status VARCHAR(20) NOT NULL,

    sent_at TIMESTAMP NULL,

    error_message VARCHAR(1000),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_by VARCHAR(100),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_by VARCHAR(100),

    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_patient
    ON notification(patient_id);

CREATE INDEX idx_notification_status
    ON notification(notification_status);

CREATE INDEX idx_notification_type
    ON notification(notification_type);

CREATE INDEX idx_notification_created_at
    ON notification(created_at);

ALTER TABLE notification
    ADD CONSTRAINT fk_notification_patient
        FOREIGN KEY (patient_id)
            REFERENCES patient(id);