CREATE TABLE doctor_leave (

                              id BIGINT AUTO_INCREMENT PRIMARY KEY,

                              doctor_id BIGINT NOT NULL,

                              from_date DATE NOT NULL,

                              to_date DATE NOT NULL,

                              number_of_days INT NOT NULL,

                              leave_type VARCHAR(30) NOT NULL,

                              status VARCHAR(20) NOT NULL,

                              reason VARCHAR(500),

                              active BOOLEAN DEFAULT TRUE,

                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                  ON UPDATE CURRENT_TIMESTAMP,

                              CONSTRAINT fk_leave_doctor
                                  FOREIGN KEY (doctor_id)
                                      REFERENCES doctor(id),

                              CONSTRAINT uk_doctor_leave
                                  UNIQUE(doctor_id, leave_date)
);