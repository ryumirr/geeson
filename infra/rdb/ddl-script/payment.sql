CREATE DATABASE payment_db;

USE payment_db;

CREATE TABLE payment_methods (
                                         method_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         customer_id BIGINT,
                                         type VARCHAR(50) NOT NULL, -- CARD, ACCOUNT, MOBILE, PAYPAL 등
                                         card_code VARCHAR(20) NOT NULL,
                                         provider VARCHAR(100), -- 예: KCP, NICE, Toss
                                         masked_number VARCHAR(50),
                                         expiration_date DATE,
                                         billing_key VARCHAR(255), -- 정기결제용 토큰
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
                                        payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        order_id BIGINT NOT NULL,
                                        amount DECIMAL(10, 2) NOT NULL,
                                        currency VARCHAR(10) DEFAULT 'KRW',
                                        status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, COMPLETED, FAILED, CANCELLED
                                        payment_method_id BIGINT,
                                        requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        completed_at TIMESTAMP,
                                        FOREIGN KEY (payment_method_id) REFERENCES payment_methods(method_id)
);

CREATE TABLE payment_status_history (
                                        history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        payment_id BIGINT NOT NULL,
                                        previous_status VARCHAR(50),
                                        new_status VARCHAR(50),
                                        changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        reason TEXT,
                                        FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
);

CREATE TABLE payment_gateways (
                                        gateway_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
                                        api_url VARCHAR(255),
                                        vendor_code VARCHAR(50),
                                        active BOOLEAN DEFAULT TRUE
);

CREATE TABLE transactions (
                              transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              payment_id BIGINT NOT NULL,
                              gateway_id BIGINT NOT NULL,
                              transaction_type VARCHAR(50), -- APPROVE, CANCEL, REFUND
                              transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              amount DECIMAL(10, 2),
                              pg_transaction_id VARCHAR(255),
                              result_code VARCHAR(50),
                              result_message TEXT,
                              FOREIGN KEY (payment_id) REFERENCES payments(payment_id),
                              FOREIGN KEY (gateway_id) REFERENCES payment_gateways(gateway_id)
);

CREATE TABLE refunds (
                         refund_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         payment_id BIGINT NOT NULL,
                         refund_amount DECIMAL(10, 2),
                         refund_reason TEXT,
                         status VARCHAR(50) DEFAULT 'REQUESTED', -- REQUESTED, COMPLETED, FAILED
                         requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         completed_at TIMESTAMP,
                         FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
);

CREATE TABLE billing_keys (
                              billing_key_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              customer_id BIGINT,
                              provider VARCHAR(100),
                              token VARCHAR(255), -- PG사에서 발급된 결제 토큰
                              expires_at DATE,
                              status VARCHAR(50) DEFAULT 'ACTIVE',
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment_errors (
                                error_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                payment_id BIGINT,
                                error_code VARCHAR(50),
                                error_message TEXT,
                                occurred_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
);

CREATE TABLE settlements (
                             settlement_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             payment_id BIGINT NOT NULL,
                             seller_id BIGINT NOT NULL,
                             settlement_amount DECIMAL(10, 2),
                             fee_amount DECIMAL(10, 2),
                             settlement_status VARCHAR(50) DEFAULT 'PENDING',
                             scheduled_date DATE,
                             settled_at TIMESTAMP,
                             FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
);

CREATE TABLE receipts (
                          receipt_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          payment_id BIGINT NOT NULL,
                          receipt_url VARCHAR(500),
                          issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (payment_id) REFERENCES payments(payment_id)
);