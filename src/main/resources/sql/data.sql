-- 无人柜系统初始化数据

USE smart_cabinet;

-- 插入管理员用户
INSERT INTO users (username, password_hash, phone, email, role, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8ioctKi99JE.HnCdtpbCNcqdUFb56', '13800138000', 'admin@smartcabinet.com', 'ADMIN', 0);

-- 插入商品分类
INSERT INTO categories (name, description, parent_id, sort_order) VALUES 
('饮料', '各类饮品', NULL, 1),
('零食', '休闲零食', NULL, 2),
('日用品', '日常用品', NULL, 3),
('碳酸饮料', '汽水类饮品', 1, 1),
('果汁', '纯天然果汁', 1, 2),
('茶饮', '茶类饮品', 1, 3),
('薯片', '油炸薯片', 2, 1),
('坚果', '各类坚果', 2, 2),
('糖果', '各种糖果', 2, 3);

-- 插入示例商品
INSERT INTO products (name, category_id, price, stock, image_url, description, status) VALUES 
('可口可乐 330ml', 4, 3.50, 100, '/images/products/cola.jpg', '经典可口可乐', 'ON_SHELF'),
('百事可乐 330ml', 4, 3.50, 80, '/images/products/pepsi.jpg', '百事可乐经典口味', 'ON_SHELF'),
('雪碧 330ml', 4, 3.00, 90, '/images/products/sprite.jpg', '清爽柠檬汽水', 'ON_SHELF'),
('农夫山泉 550ml', 1, 2.00, 150, '/images/products/nongfu.jpg', '天然矿泉水', 'ON_SHELF'),
('橙汁 300ml', 5, 5.00, 60, '/images/products/orange.jpg', '100%纯橙汁', 'ON_SHELF'),
('绿茶 500ml', 6, 3.50, 70, '/images/products/greentea.jpg', '清香绿茶', 'ON_SHELF'),
('薯片 70g', 7, 6.50, 40, '/images/products/chips.jpg', '经典原味薯片', 'ON_SHELF'),
('花生米 80g', 8, 8.00, 30, '/images/products/peanuts.jpg', '香脆花生', 'ON_SHELF'),
('巧克力 50g', 9, 12.00, 25, '/images/products/chocolate.jpg', '浓郁黑巧克力', 'ON_SHELF'),
('口香糖', 9, 4.50, 50, '/images/products/gum.jpg', '清新口气', 'ON_SHELF');

-- 插入示例设备
INSERT INTO devices (device_id, device_name, model, location, address, status, ip_address, firmware_version) VALUES 
('SC001', '智能柜1号', 'SC-2024-A', '办公楼A座1楼大厅', '北京市朝阳区xxx路xxx号办公楼A座1楼大厅', 'ONLINE', '192.168.1.101', 'v1.2.3'),
('SC002', '智能柜2号', 'SC-2024-A', '办公楼B座2楼休息区', '北京市朝阳区xxx路xxx号办公楼B座2楼休息区', 'ONLINE', '192.168.1.102', 'v1.2.3'),
('SC003', '智能柜3号', 'SC-2024-B', '学校食堂', '北京市海淀区xxx学校食堂一楼', 'OFFLINE', '192.168.1.103', 'v1.2.2'),
('SC004', '智能柜4号', 'SC-2024-A', '地铁站A口', '北京市东城区地铁xx站A出口', 'MAINTENANCE', '192.168.1.104', 'v1.2.3'),
('SC005', '智能柜5号', 'SC-2024-B', '商场1楼', '北京市西城区xxx商场1楼中庭', 'ONLINE', '192.168.1.105', 'v1.2.3');

-- 插入库存数据
INSERT INTO inventory (device_id, product_id, slot_number, current_stock, max_capacity, warning_threshold) VALUES 
('SC001', 1, 'A01', 15, 20, 5),
('SC001', 2, 'A02', 12, 20, 5),
('SC001', 3, 'A03', 18, 20, 5),
('SC001', 4, 'B01', 25, 30, 8),
('SC001', 5, 'B02', 8, 15, 3),
('SC001', 6, 'B03', 10, 15, 3),
('SC001', 7, 'C01', 6, 10, 2),
('SC001', 8, 'C02', 4, 10, 2),
('SC002', 1, 'A01', 20, 20, 5),
('SC002', 2, 'A02', 15, 20, 5),
('SC002', 4, 'B01', 28, 30, 8),
('SC002', 9, 'D01', 3, 8, 2),
('SC002', 10, 'D02', 12, 15, 3);

-- 插入示例订单
INSERT INTO orders (order_no, user_id, device_id, total_amount, status, payment_method, payment_time) VALUES 
('ORD20240101001', 1, 'SC001', 10.00, 'COMPLETED', 'WECHAT', '2024-01-01 10:30:00'),
('ORD20240101002', 1, 'SC001', 6.50, 'COMPLETED', 'ALIPAY', '2024-01-01 14:20:00'),
('ORD20240101003', 1, 'SC002', 15.50, 'PAID', 'WECHAT', '2024-01-01 16:45:00');

-- 插入订单商品
INSERT INTO order_items (order_id, product_id, product_name, product_price, quantity, subtotal) VALUES 
(1, 1, '可口可乐 330ml', 3.50, 2, 7.00),
(1, 4, '农夫山泉 550ml', 2.00, 1, 2.00),
(1, 10, '口香糖', 4.50, 1, 4.50),
(2, 7, '薯片 70g', 6.50, 1, 6.50),
(3, 9, '巧克力 50g', 12.00, 1, 12.00),
(3, 1, '可口可乐 330ml', 3.50, 1, 3.50);

-- 插入操作日志
INSERT INTO operation_logs (user_id, operation_type, description, device_id, order_id, result, client_ip) VALUES 
(1, 'LOGIN', '用户登录', NULL, NULL, 'SUCCESS', '192.168.1.100'),
(1, 'PURCHASE', '购买商品', 'SC001', 1, 'SUCCESS', '192.168.1.100'),
(1, 'PURCHASE', '购买商品', 'SC001', 2, 'SUCCESS', '192.168.1.100'),
(1, 'PURCHASE', '购买商品', 'SC002', 3, 'SUCCESS', '192.168.1.100');
