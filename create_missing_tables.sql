USE smart_cabinet;

-- 创建易通无人柜订单表
CREATE TABLE hibianli_orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    order_code VARCHAR(50) UNIQUE NOT NULL COMMENT '订单编号',
    hbl_order_no VARCHAR(50) COMMENT '易通订单号',
    event_id BIGINT COMMENT '关联事件ID',
    device_code VARCHAR(50) NOT NULL COMMENT '设备编码',
    user_id VARCHAR(100) NOT NULL COMMENT '用户ID',
    order_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
    product_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品金额',
    promotion_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    actual_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
    order_status ENUM('PENDING', 'PAID', 'COMPLETED', 'CANCELLED', 'REFUNDED') DEFAULT 'PENDING' COMMENT '订单状态',
    pay_status ENUM('UNPAID', 'PAID', 'REFUNDING', 'REFUNDED') DEFAULT 'UNPAID' COMMENT '支付状态',
    pay_type TINYINT DEFAULT 0 COMMENT '支付类型',
    third_pay_order_id VARCHAR(100) COMMENT '第三方支付订单号',
    channel_type VARCHAR(20) COMMENT '支付渠道',
    trade_type VARCHAR(20) COMMENT '交易类型',
    share_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '分账金额',
    trade_time VARCHAR(20) COMMENT '支付时间',
    expired_at TIMESTAMP COMMENT '订单过期时间',
    nonce_str VARCHAR(50) COMMENT '随机字符串',
    request_serial VARCHAR(50) COMMENT '请求序列号',
    time_stamp VARCHAR(20) COMMENT '时间戳',
    sign VARCHAR(100) COMMENT '签名',
    company_id INT COMMENT '公司ID',
    remark TEXT COMMENT '订单备注',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_code (order_code),
    INDEX idx_user_id (user_id),
    INDEX idx_order_status (order_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='易通无人柜订单表';

-- 创建易通无人柜订单商品明细表
CREATE TABLE hibianli_order_items (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单商品ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    order_code VARCHAR(50) NOT NULL COMMENT '订单编号',
    goods_id BIGINT NOT NULL COMMENT '云库商品ID',
    hbl_sku_id VARCHAR(50) COMMENT '易通SKU ID',
    barcode VARCHAR(50) COMMENT '商品条码',
    ext_sku_code VARCHAR(100) COMMENT '第三方映射CODE',
    goods_name VARCHAR(200) NOT NULL COMMENT '商品名称(快照)',
    goods_sku VARCHAR(100) COMMENT '商品规格(快照)',
    goods_image VARCHAR(500) COMMENT '商品图片(快照)',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '商品单价(快照)',
    raw_price DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品原价(快照)',
    quantity INT NOT NULL DEFAULT 1 COMMENT '购买数量',
    subtotal DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    discount_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    actual_amount DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    goods_status TINYINT COMMENT '商品状态 1-正常 2-非友好 10-未上架',
    recognition_status TINYINT DEFAULT 1 COMMENT '识别状态 1-正常识别 2-异常识别',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_order_id (order_id),
    INDEX idx_order_code (order_code),
    INDEX idx_goods_id (goods_id),
    INDEX idx_barcode (barcode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='易通无人柜订单商品明细表';

-- 插入一些测试数据
INSERT INTO hibianli_orders (order_code, device_code, user_id, order_amount, actual_amount, order_status, pay_status) VALUES
('ORD001', 'DEV001', 'USER001', 15.50, 15.50, 'COMPLETED', 'PAID'),
('ORD002', 'DEV002', 'USER001', 25.00, 25.00, 'PAID', 'PAID'),
('ORD003', 'DEV001', 'USER002', 8.50, 8.50, 'PENDING', 'UNPAID');

INSERT INTO hibianli_order_items (order_code, goods_id, goods_name, goods_sku, unit_price, quantity, subtotal, actual_amount) VALUES
('ORD001', 1, '可口可乐', '330ml', 3.50, 2, 7.00, 7.00),
('ORD001', 2, '薯片', '原味100g', 8.50, 1, 8.50, 8.50),
('ORD002', 3, '矿泉水', '500ml', 2.50, 10, 25.00, 25.00),
('ORD003', 4, '面包', '全麦面包', 8.50, 1, 8.50, 8.50); 