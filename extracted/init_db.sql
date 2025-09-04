-- Create tables for smart cabinet system
USE smart_cabinet;

-- Users table
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(64) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    openid VARCHAR(100) UNIQUE,
    avatar VARCHAR(255),
    credit_score INT DEFAULT 500,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    status INT DEFAULT 0,
    deleted INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_phone (phone),
    INDEX idx_email (email),
    INDEX idx_openid (openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Categories table
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id BIGINT,
    sort_order INT DEFAULT 0,
    deleted INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Products table
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(255),
    description TEXT,
    status ENUM('ON_SHELF', 'OFF_SHELF') DEFAULT 'ON_SHELF',
    version INT DEFAULT 1,
    deleted INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert test data
INSERT INTO users (username, password_hash, phone, email, role, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKi99JE.HnCdtpbCNcqdUFb56', '13800138000', 'admin@smartcabinet.com', 'ADMIN', 0);

INSERT INTO categories (name, description, parent_id, sort_order) VALUES 
('饮料', '各类饮品', NULL, 1),
('零食', '休闲零食', NULL, 2);

INSERT INTO products (name, category_id, price, stock, description, status) VALUES 
('可口可乐 330ml', 1, 3.50, 100, '经典可口可乐', 'ON_SHELF'),
('百事可乐 330ml', 1, 3.50, 80, '百事可乐经典口味', 'ON_SHELF');
