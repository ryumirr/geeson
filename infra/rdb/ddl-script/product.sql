CREATE DATABASE product_db;

USE product_db;

CREATE TABLE products (
                          product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          sku VARCHAR(50) UNIQUE,
                          brand_id BIGINT,
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product_details (
                                 product_id BIGINT PRIMARY KEY,
                                 description TEXT,
                                 spec TEXT,
                                 warranty TEXT,
                                 FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE product_categories (
                                    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL,
                                    parent_id BIGINT,
                                    depth INT DEFAULT 0
);

CREATE TABLE product_category_map (
                                      product_id BIGINT,
                                      category_id BIGINT,
                                      PRIMARY KEY (product_id, category_id),
                                      FOREIGN KEY (product_id) REFERENCES products(product_id),
                                      FOREIGN KEY (category_id) REFERENCES product_categories(category_id)
);

CREATE TABLE product_prices (
                                product_id BIGINT PRIMARY KEY,
                                price DECIMAL(10, 2) NOT NULL,
                                discount_price DECIMAL(10, 2),
                                currency VARCHAR(10) DEFAULT 'KRW',
                                price_updated_at TIMESTAMP,
                                FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE product_options (
                                 option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 product_id BIGINT,
                                 name VARCHAR(100) NOT NULL,
                                 FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE product_option_values (
                                       value_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       option_id BIGINT,
                                       value VARCHAR(100),
                                       FOREIGN KEY (option_id) REFERENCES product_options(option_id)
);

CREATE TABLE product_variants (
                                  variant_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  product_id BIGINT,
                                  sku VARCHAR(50) UNIQUE,
                                  option_combination JSON, -- 예: {"색상": "블랙", "사이즈": "M"}
                                  price DECIMAL(10,2),
                                  stock INT DEFAULT 0,
                                  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE product_images (
                                image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                product_id BIGINT,
                                image_url VARCHAR(500),
                                sort_order INT DEFAULT 0,
                                is_thumbnail BOOLEAN DEFAULT FALSE,
                                FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE brands (
                        brand_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) UNIQUE NOT NULL,
                        description TEXT
);

CREATE TABLE tags (
                      tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) UNIQUE
);

CREATE TABLE product_tag_map (
                                 product_id BIGINT,
                                 tag_id BIGINT,
                                 PRIMARY KEY (product_id, tag_id),
                                 FOREIGN KEY (product_id) REFERENCES products(product_id),
                                 FOREIGN KEY (tag_id) REFERENCES tags(tag_id)
);

CREATE TABLE product_reviews (
                                 review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 product_id BIGINT,
                                 customer_id BIGINT,
                                 rating INT CHECK (rating BETWEEN 1 AND 5),
                                 comment TEXT,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_qna (
                             qna_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             product_id BIGINT,
                             customer_id BIGINT,
                             question TEXT,
                             answer TEXT,
                             status VARCHAR(50) DEFAULT 'PENDING',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_visibility (
                                    product_id BIGINT PRIMARY KEY,
                                    is_visible BOOLEAN DEFAULT TRUE,
                                    visibility_start TIMESTAMP,
                                    visibility_end TIMESTAMP,
                                    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO products (
    product_id,
    name,
    description,
    sku,
    category_id,
    price,
    created_at,
    updated_at
) VALUES (
    1,
    'test',
    'test-name',
    '1',
    NULL,
    NULL,
    '2025-07-02 11:27:50',
    '2025-07-02 11:27:50'
);
