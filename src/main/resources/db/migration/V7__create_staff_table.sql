CREATE TABLE staff (

                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       user_id BIGINT NOT NULL UNIQUE,

                       employee_code VARCHAR(20) UNIQUE,

                       first_name VARCHAR(50) NOT NULL,

                       last_name VARCHAR(50),

                       gender VARCHAR(20),

                       mobile VARCHAR(15),

                       email VARCHAR(100),

                       department VARCHAR(50),

                       designation VARCHAR(50),

                       joining_date DATE,

                       active BOOLEAN DEFAULT TRUE,

                       created_at TIMESTAMP,

                       updated_at TIMESTAMP,

                       created_by VARCHAR(100),

                       updated_by VARCHAR(100),

                       version BIGINT,

                       CONSTRAINT fk_staff_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
);