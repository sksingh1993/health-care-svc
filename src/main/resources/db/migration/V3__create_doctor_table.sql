CREATE TABLE doctor (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        doctor_code VARCHAR(20) UNIQUE NOT NULL,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100),
                        gender VARCHAR(10) NOT NULL,
                        date_of_birth DATE,
                        mobile VARCHAR(15) UNIQUE NOT NULL,
                        email VARCHAR(100) UNIQUE,
                        specialization VARCHAR(100) NOT NULL,
                        qualification VARCHAR(200),
                        experience_years INT,
                        consultation_fee DECIMAL(10,2) NOT NULL,
                        registration_number VARCHAR(100) UNIQUE NOT NULL,
                        address TEXT,
                        active BOOLEAN DEFAULT TRUE,
                        version BIGINT DEFAULT 0,
                        created_by BIGINT,
                        updated_by BIGINT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_doctor_name
    ON doctor(first_name, last_name);

CREATE INDEX idx_doctor_mobile
    ON doctor(mobile);

CREATE INDEX idx_doctor_specialization
    ON doctor(specialization);