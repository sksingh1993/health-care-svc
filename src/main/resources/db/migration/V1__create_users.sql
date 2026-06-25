CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       email VARCHAR(150),
                       mobile VARCHAR(20),
                       active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       role_code VARCHAR(50) NOT NULL UNIQUE,
                       role_name VARCHAR(100) NOT NULL,
                       description VARCHAR(255)
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,

                            PRIMARY KEY(user_id, role_id),

                            CONSTRAINT fk_user_role_user
                                FOREIGN KEY(user_id)
                                    REFERENCES users(id),

                            CONSTRAINT fk_user_role_role
                                FOREIGN KEY(role_id)
                                    REFERENCES roles(id)
);

INSERT INTO roles
(role_code,role_name)
VALUES
    ('ADMIN','Administrator'),
    ('DOCTOR','Doctor'),
    ('RECEPTIONIST','Receptionist');