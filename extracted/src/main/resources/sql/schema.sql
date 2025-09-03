-- 无人柜系统数据库表结构
-- 创建数据库
CREATE DATABASE IF NOT EXISTS smart_cabinet DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_cabinet;

-- 用户表
CREATE TABLE users (
    
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password_hash VARCHAR(64) NOT NULL COMMENT '密码哈希',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    openid VARCHAR(100) UNIQUE COMMENT '微信OpenID',
    avatar VARCHAR(255) COMMENT '头像URL',
    credit_score INT DEFAULT 500 COMMENT '信用分',
    role ENUM('USER', 'ADMIN') DEFAULT 'USER' COMMENT '角色',
    status INT DEFAULT 0 COMMENT '账户状态 0-正常 1-锁定',
    deleted INT DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_phone (phone),
    INDEX idx_email (email),
    INDEX idx_openid (openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 商品分类表
CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    description TEXT COMMENT '分类描述',
    parent_id BIGINT COMMENT '父分类ID',
    sort_order INT DEFAULT 0 COMMENT '排序字段',
    deleted INT DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    name VARCHAR(100) NOT NULL COMMENT '商品名称',
    category_id BIGINT COMMENT '分类ID',
    price DECIMAL(10,2) NOT NULL COMMENT '售价',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    image_url VARCHAR(255) COMMENT '商品图片URL',
    description TEXT COMMENT '商品描述',
    status ENUM('ON_SHELF', 'OFF_SHELF') DEFAULT 'ON_SHELF' COMMENT '状态',
    version INT DEFAULT 1 COMMENT '版本号(乐观锁)',
    deleted INT DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';

-- 设备表
CREATE TABLE devices (
    device_id VARCHAR(50) PRIMARY KEY COMMENT '设备ID',
    device_name VARCHAR(100) COMMENT '设备名称',
    model VARCHAR(50) COMMENT '设备型号',
    location VARCHAR(255) COMMENT '安装位置',
    address VARCHAR(500) COMMENT '详细地址',
    status ENUM('ONLINE', 'OFFLINE', 'FAULT', 'MAINTENANCE') DEFAULT 'OFFLINE' COMMENT '状态',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    firmware_version VARCHAR(20) COMMENT '固件版本',
    last_heartbeat TIMESTAMP COMMENT '最后心跳时间',
    temperature_max DECIMAL(5,2) DEFAULT 35.0 COMMENT '温度阈值上限',
    temperature_min DECIMAL(5,2) DEFAULT 5.0 COMMENT '温度阈值下限',
    humidity_max DECIMAL(5,2) DEFAULT 80.0 COMMENT '湿度阈值上限',
    humidity_min DECIMAL(5,2) DEFAULT 30.0 COMMENT '湿度阈值下限',
    deleted INT DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_status (status),
    INDEX idx_location (location),
    INDEX idx_heartbeat (last_heartbeat)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';

-- 设备状态记录表
CREATE TABLE device_status (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    temperature DECIMAL(5,2) COMMENT '温度(℃)',
    humidity DECIMAL(5,2) COMMENT '湿度(%)',
    door_status JSON COMMENT '柜门状态',
    sensor_data JSON COMMENT '传感器数据',
    battery_level INT COMMENT '电池电量',
    signal_strength INT COMMENT '网络信号强度',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_device_time (device_id, created_at),
    FOREIGN KEY (device_id) REFERENCES devices(device_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备状态记录表';

-- 库存表
CREATE TABLE inventory (
    inventory_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '库存ID',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    slot_number VARCHAR(10) NOT NULL COMMENT '货道编号',
    current_stock INT NOT NULL DEFAULT 0 COMMENT '当前库存数量',
    max_capacity INT NOT NULL COMMENT '最大容量',
    warning_threshold INT DEFAULT 5 COMMENT '预警阈值',
    last_refill_time TIMESTAMP COMMENT '最后补货时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_device_product (device_id, product_id, slot_number),
    INDEX idx_device (device_id),
    INDEX idx_product (product_id),
    FOREIGN KEY (device_id) REFERENCES devices(device_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

-- 订单表
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    order_no VARCHAR(32) UNIQUE NOT NULL COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '总金额',
    status ENUM('PENDING_PAYMENT', 'PAID', 'COMPLETED', 'REFUNDED', 'CANCELLED') DEFAULT 'PENDING_PAYMENT' COMMENT '订单状态',
    payment_method VARCHAR(20) COMMENT '支付方式',
    payment_time DATETIME COMMENT '支付时间',
    payment_transaction_id VARCHAR(100) COMMENT '支付交易号',
    remark VARCHAR(500) COMMENT '备注信息',
    deleted INT DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user (user_id),
    INDEX idx_device (device_id),
    INDEX idx_status (status),
    INDEX idx_order_no (order_no),
    INDEX idx_payment_time (payment_time),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (device_id) REFERENCES devices(device_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单信息表';

-- 订单商品表
CREATE TABLE order_items (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单商品ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '商品名称(快照)',
    product_price DECIMAL(10,2) NOT NULL COMMENT '商品价格(快照)',
    quantity INT NOT NULL COMMENT '购买数量',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_order (order_id),
    INDEX idx_product (product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品表';

-- 操作日志表
CREATE TABLE operation_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    operation_type VARCHAR(50) NOT NULL COMMENT '操作类型',
    description VARCHAR(500) COMMENT '操作描述',
    device_id VARCHAR(50) COMMENT '设备ID',
    order_id BIGINT COMMENT '订单ID',
    result ENUM('SUCCESS', 'FAILURE') NOT NULL COMMENT '操作结果',
    client_ip VARCHAR(50) COMMENT '客户端IP',
    user_agent VARCHAR(500) COMMENT '用户代理',
    extra_data TEXT COMMENT '额外数据(JSON格式)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user (user_id),
    INDEX idx_device (device_id),
    INDEX idx_type (operation_type),
    INDEX idx_time (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
