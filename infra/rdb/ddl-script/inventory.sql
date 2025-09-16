CREATE TABLE warehouses (
    warehouse_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    capacity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    total_quantity INT DEFAULT 0,
    reserved_quantity INT DEFAULT 0,
    available_quantity INT GENERATED ALWAYS AS (total_quantity - reserved_quantity) STORED,
    reorder_level INT DEFAULT 0,
    reorder_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id)
);

CREATE TABLE inventory_items (
    inventory_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    batch_lot_id BIGINT,
    serial_number VARCHAR(255) UNIQUE,
    status VARCHAR(50) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

CREATE TABLE inventory_reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    reserved_quantity INT NOT NULL,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    status VARCHAR(50) DEFAULT 'RESERVED',
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

CREATE TABLE stock_movements (
  movement_id BIGINT NOT NULL AUTO_INCREMENT,
  inventory_id BIGINT NOT NULL,
  movement_type ENUM('IN','OUT','TRANSFER') NOT NULL,
  quantity INT NOT NULL,
  movement_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  description TEXT,
  reference_id VARCHAR(128) NOT NULL,
  PRIMARY KEY (movement_id),
  UNIQUE KEY uq_reference_id (reference_id),
  KEY inventory_id (inventory_id)
);

CREATE TABLE suppliers (
    supplier_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE purchase_orders (
    purchase_order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PENDING',
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id)
);

CREATE TABLE inventory_audit_logs (
    audit_log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    action VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    previous_quantity INT NOT NULL,
    current_quantity INT NOT NULL,
    performed_by VARCHAR(255),
    performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

CREATE TABLE inventory_adjustments (
    adjustment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_id BIGINT NOT NULL,
    adjustment_quantity INT NOT NULL,
    reason TEXT,
    adjusted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id)
);

-- Test Data
INSERT INTO inventory_db.inventory (
    inventory_id,
    warehouse_id,
    total_quantity,
    reserved_quantity,
    reorder_level,
    reorder_quantity,
    created_at,
    updated_at
) VALUES (
    1,
    1,
    100,
    2,
    0,
    0,
    '2025-07-02 11:28:31',
    '2025-07-02 11:28:31'
);
