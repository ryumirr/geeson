CREATE DATABASE user_db;

USE user_db;

CREATE TABLE users (
                       id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                       username        VARCHAR(100) NOT NULL UNIQUE,
                       email           VARCHAR(255) NOT NULL UNIQUE,
                       password_hash   VARCHAR(255) NOT NULL, -- bcrypt or scrypt 해시 저장
                       status          ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
                       email_verified  BOOLEAN DEFAULT FALSE,
                       phone_number    VARCHAR(20),
                       mfa_enabled     BOOLEAN DEFAULT FALSE,
                       created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (
    username,
    email,
    password_hash,
    status,
    email_verified,
    phone_number,
    mfa_enabled
) VALUES (
    'test',
    'test@example.com',
    'test', -- bcrypt hash for 'test'
    'ACTIVE',
    FALSE,
    '010-1234-5678',
    FALSE
);
