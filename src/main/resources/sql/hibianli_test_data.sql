-- 易通无人柜订单系统测试数据
-- 插入测试数据用于微信小程序订单功能演示

USE smart_cabinet;

-- 1. 插入测试商品数据
INSERT INTO hibianli_goods (goods_id, hbl_sku_id, barcode, ext_sku_code, name, sku, description, price, raw_price, image_url, status, goods_type, company_id) VALUES
(1001, '1420413387829231618', '6943878403365', '6943878403365', '可口可乐 330ml', '330ml', '经典可口可乐，清爽解腻', 3.50, 4.00, 'https://example.com/images/coca_cola_330.jpg', 1, 1, 10),
(1002, '1420413387829231619', '6902827100069', '6902827100069', '百事可乐 330ml', '330ml', '百事可乐经典口味', 3.50, 4.00, 'https://example.com/images/pepsi_330.jpg', 1, 1, 10),
(1003, '1420413387829231620', '6901234567890', '6901234567890', '康师傅绿茶 500ml', '500ml', '清香绿茶，天然健康', 3.00, 3.50, 'https://example.com/images/green_tea_500.jpg', 1, 1, 10),
(1004, '1420413387829231621', '6901234567891', '6901234567891', '农夫山泉 550ml', '550ml', '天然矿泉水，甘甜清冽', 2.00, 2.50, 'https://example.com/images/nongfu_550.jpg', 1, 1, 10),
(1005, '1420413387829231622', '6901234567892', '6901234567892', '士力架巧克力 51g', '51g', '横扫饥饿，做回自己', 4.50, 5.00, 'https://example.com/images/snickers_51g.jpg', 1, 1, 10),
(1006, '1420413387829231623', '6901234567893', '6901234567893', '乐事薯片原味 70g', '70g', '乐事薯片，乐享美味', 6.00, 7.00, 'https://example.com/images/lays_70g.jpg', 1, 1, 10),
(1007, '1420413387829231624', '6901234567894', '6901234567894', '旺旺雪饼 150g', '150g', '香脆可口的米果', 5.50, 6.50, 'https://example.com/images/want_want_150g.jpg', 1, 1, 10),
(1008, '1420413387829231625', '6901234567895', '6901234567895', '德芙巧克力 43g', '43g', '丝滑德芙，纵享丝滑', 8.50, 10.00, 'https://example.com/images/dove_43g.jpg', 1, 1, 10);

-- 2. 插入测试设备数据
INSERT INTO hibianli_devices (device_code, ext_code, device_name, device_model, net_status, ele_status, door_status, is_forbidden, location, address, merchant_id, company_id) VALUES
('DEV001', 'EXT001', '办公楼1F智能柜', 'HBL-2024-A1', 1, 1, 2, 0, '办公楼一楼大厅', '北京市朝阳区建国路1号办公楼', 1, 10),
('DEV002', 'EXT002', '食堂智能柜', 'HBL-2024-A1', 1, 1, 2, 0, '员工食堂', '北京市朝阳区建国路1号食堂', 1, 10),
('DEV003', 'EXT003', '宿舍楼智能柜', 'HBL-2024-B1', 1, 1, 2, 0, '宿舍楼1楼', '北京市朝阳区建国路1号宿舍楼', 1, 10);

-- 3. 插入设备商品关联数据
INSERT INTO hibianli_device_goods (device_code, goods_id, hbl_sku_id, slot_number, stock_quantity, max_capacity, status, company_id) VALUES
('DEV001', 1001, '1420413387829231618', 'A01', 15, 20, 1, 10),
('DEV001', 1002, '1420413387829231619', 'A02', 12, 20, 1, 10),
('DEV001', 1003, '1420413387829231620', 'B01', 18, 25, 1, 10),
('DEV001', 1004, '1420413387829231621', 'B02', 20, 25, 1, 10),
('DEV001', 1005, '1420413387829231622', 'C01', 10, 15, 1, 10),
('DEV001', 1006, '1420413387829231623', 'C02', 8, 15, 1, 10),
('DEV002', 1001, '1420413387829231618', 'A01', 10, 20, 1, 10),
('DEV002', 1002, '1420413387829231619', 'A02', 9, 20, 1, 10),
('DEV002', 1007, '1420413387829231624', 'B01', 12, 15, 1, 10),
('DEV002', 1008, '1420413387829231625', 'B02', 6, 15, 1, 10);

-- 4. 插入测试用户数据
INSERT INTO hibianli_users (hbl_user_id, openid, nickname, avatar_url, phone, gender, register_source, company_id) VALUES
('YT_USER_001', 'oZKmq0tcsP zhMejIvmij43C2rAp0', '微信用户001', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example1.jpg', '13800138001', 1, 'MINIPROGRAM', 10),
('YT_USER_002', 'oZKmq0tcsP zhMejIvmij43C2rAp1', '微信用户002', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example2.jpg', '13800138002', 2, 'MINIPROGRAM', 10),
('YT_USER_003', 'oZKmq0tcsP zhMejIvmij43C2rAp2', '微信用户003', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example3.jpg', '13800138003', 1, 'MINIPROGRAM', 10);

-- 5. 插入测试事件数据
INSERT INTO hibianli_events (hbl_event_no, ext_event_no, device_code, user_id, event_type, pay_type, user_type, status, nonce_str, request_serial, time_stamp, company_id) VALUES
('YT20241218001', 'EXT20241218001', 'DEV001', 'YT_USER_001', 'OPEN_DOOR', 7, 1, 2, 'abc123def456', '20241218150001', '20241218150001000', 10),
('YT20241218002', 'EXT20241218002', 'DEV001', 'YT_USER_002', 'OPEN_DOOR', 7, 1, 2, 'def456ghi789', '20241218151001', '20241218151001000', 10),
('YT20241218003', 'EXT20241218003', 'DEV002', 'YT_USER_003', 'OPEN_DOOR', 6, 1, 2, 'ghi789jkl012', '20241218152001', '20241218152001000', 10);

-- 6. 插入测试订单数据
INSERT INTO hibianli_orders (order_code, hbl_order_no, event_id, device_code, user_id, order_amount, product_amount, promotion_amount, actual_amount, order_status, pay_status, pay_type, channel_type, trade_type, nonce_str, request_serial, time_stamp, company_id) VALUES
('ORD20241218001', 'YT_ORD_001', 1, 'DEV001', 'YT_USER_001', 7.00, 8.00, 1.00, 7.00, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'abc123def456', '20241218150001', '20241218150001000', 10),
('ORD20241218002', 'YT_ORD_002', 2, 'DEV001', 'YT_USER_002', 6.50, 7.00, 0.50, 6.50, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'def456ghi789', '20241218151001', '20241218151001000', 10),
('ORD20241218003', 'YT_ORD_003', 3, 'DEV002', 'YT_USER_003', 11.50, 13.00, 1.50, 11.50, 'COMPLETED', 'PAID', 6, 'alipay', 'PAY', 'ghi789jkl012', '20241218152001', '20241218152001000', 10),
('ORD20241218004', 'YT_ORD_004', NULL, 'DEV001', 'YT_USER_001', 5.50, 6.00, 0.50, 5.50, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'jkl012mno345', '20241218160001', '20241218160001000', 10),
('ORD20241218005', 'YT_ORD_005', NULL, 'DEV002', 'YT_USER_002', 9.00, 10.00, 1.00, 9.00, 'PAID', 'PAID', 7, 'wxpay', 'PAY', 'mno345pqr678', '20241218161001', '20241218161001000', 10);

-- 7. 插入测试订单商品明细数据
INSERT INTO hibianli_order_items (order_id, order_code, goods_id, hbl_sku_id, barcode, goods_name, goods_sku, unit_price, quantity, subtotal, actual_amount, goods_status) VALUES
-- 订单1的商品
(1, 'ORD20241218001', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 1, 3.50, 3.50, 1),
(1, 'ORD20241218001', 1005, '1420413387829231622', '6901234567892', '士力架巧克力 51g', '51g', 4.50, 1, 4.50, 3.50, 1),
-- 订单2的商品
(2, 'ORD20241218002', 1002, '1420413387829231619', '6902827100069', '百事可乐 330ml', '330ml', 3.50, 1, 3.50, 3.50, 1),
(2, 'ORD20241218002', 1003, '1420413387829231620', '6901234567890', '康师傅绿茶 500ml', '500ml', 3.00, 1, 3.00, 3.00, 1),
-- 订单3的商品
(3, 'ORD20241218003', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 2, 7.00, 6.00, 1),
(3, 'ORD20241218003', 1007, '1420413387829231624', '6901234567894', '旺旺雪饼 150g', '150g', 5.50, 1, 5.50, 5.50, 1),
-- 订单4的商品
(4, 'ORD20241218004', 1006, '1420413387829231623', '6901234567893', '乐事薯片原味 70g', '70g', 6.00, 1, 6.00, 5.50, 1),
-- 订单5的商品
(5, 'ORD20241218005', 1008, '1420413387829231625', '6901234567895', '德芙巧克力 43g', '43g', 8.50, 1, 8.50, 8.00, 1),
(5, 'ORD20241218005', 1004, '1420413387829231621', '6901234567891', '农夫山泉 550ml', '550ml', 2.00, 1, 2.00, 1.00, 1);

-- 8. 插入识别结果测试数据
INSERT INTO hibianli_recognition_results (event_id, hbl_event_no, ext_event_no, device_code, recog_code, anomaly_code, video_url, recognition_time, company_id) VALUES
(1, 'YT20241218001', 'EXT20241218001', 'DEV001', 1, 0, 'https://video.example.com/recognition/20241218001.mp4', '2024-12-18 15:00:30', 10),
(2, 'YT20241218002', 'EXT20241218002', 'DEV001', 1, 0, 'https://video.example.com/recognition/20241218002.mp4', '2024-12-18 15:10:30', 10),
(3, 'YT20241218003', 'EXT20241218003', 'DEV002', 1, 0, 'https://video.example.com/recognition/20241218003.mp4', '2024-12-18 15:20:30', 10);

-- 9. 插入识别商品明细测试数据
INSERT INTO hibianli_recognition_items (result_id, event_id, sku_id, barcode, ext_sku_code, goods_name, quantity, goods_status, unit_price) VALUES
-- 识别结果1的商品
(1, 1, '1420413387829231618', '6943878403365', '6943878403365', '可口可乐 330ml', 1, 1, 3.50),
(1, 1, '1420413387829231622', '6901234567892', '6901234567892', '士力架巧克力 51g', 1, 1, 4.50),
-- 识别结果2的商品
(2, 2, '1420413387829231619', '6902827100069', '6902827100069', '百事可乐 330ml', 1, 1, 3.50),
(2, 2, '1420413387829231620', '6901234567890', '6901234567890', '康师傅绿茶 500ml', 1, 1, 3.00),
-- 识别结果3的商品
(3, 3, '1420413387829231618', '6943878403365', '6943878403365', '可口可乐 330ml', 2, 1, 3.50),
(3, 3, '1420413387829231624', '6901234567894', '6901234567894', '旺旺雪饼 150g', 1, 1, 5.50);

-- 10. 更新用户统计信息
UPDATE hibianli_users SET 
    total_order_count = (
        SELECT COUNT(*) FROM hibianli_orders WHERE user_id = hibianli_users.hbl_user_id AND order_status != 'CANCELLED'
    ),
    total_order_amount = (
        SELECT COALESCE(SUM(actual_amount), 0) FROM hibianli_orders WHERE user_id = hibianli_users.hbl_user_id AND order_status = 'COMPLETED'
    );

-- 11. 更新设备库存信息（模拟商品被购买后的库存变化）
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1001;
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1005;
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1002;
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1003;
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 2 WHERE device_code = 'DEV002' AND goods_id = 1001;
UPDATE hibianli_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV002' AND goods_id = 1007;
