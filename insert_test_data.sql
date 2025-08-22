USE smart_cabinet;

-- 插入测试订单数据
INSERT INTO hibianli_orders (order_code, device_code, user_id, order_amount, actual_amount, order_status, pay_status) VALUES 
('ORD001', 'DEV001', 'USER001', 15.50, 15.50, 'COMPLETED', 'PAID'),
('ORD002', 'DEV002', 'USER001', 25.00, 25.00, 'PAID', 'PAID'),
('ORD003', 'DEV001', 'USER002', 8.50, 8.50, 'PENDING', 'UNPAID');

-- 插入测试订单商品明细数据
INSERT INTO hibianli_order_items (order_code, goods_id, goods_name, goods_sku, unit_price, quantity, subtotal, actual_amount) VALUES 
('ORD001', 1, '可口可乐', '330ml', 3.50, 2, 7.00, 7.00),
('ORD001', 2, '薯片', '原味100g', 8.50, 1, 8.50, 8.50),
('ORD002', 3, '矿泉水', '500ml', 2.50, 10, 25.00, 25.00),
('ORD003', 4, '面包', '全麦面包', 8.50, 1, 8.50, 8.50); 