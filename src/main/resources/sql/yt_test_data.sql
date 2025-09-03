-- 易通无人柜订单系统测试数据
-- 插入测试数据用于微信小程序订单功能演示

USE smart_cabinet;

-- 1. 插入测试商品数据
INSERT INTO yt_goods (goods_id, hbl_sku_id, barcode, ext_sku_code, name, sku, description, price, raw_price, image_url, status, goods_type, company_id) VALUES
(1001, '1420413387829231618', '6943878403365', '6943878403365', '可口可乐 330ml', '330ml', '经典可口可乐，清爽解腻', 3.50, 4.00, 'https://example.com/images/coca_cola_330.jpg', 1, 1, 10),
(1002, '1420413387829231619', '6902827100069', '6902827100069', '百事可乐 330ml', '330ml', '百事可乐经典口味', 3.50, 4.00, 'https://example.com/images/pepsi_330.jpg', 1, 1, 10),
(1003, '1420413387829231620', '6901234567890', '6901234567890', '康师傅绿茶 500ml', '500ml', '清香绿茶，天然健康', 3.00, 3.50, 'https://example.com/images/green_tea_500.jpg', 1, 1, 10),
(1004, '1420413387829231621', '6901234567891', '6901234567891', '农夫山泉 550ml', '550ml', '天然矿泉水，甘甜清冽', 2.00, 2.50, 'https://example.com/images/nongfu_550.jpg', 1, 1, 10),
(1005, '1420413387829231622', '6901234567892', '6901234567892', '士力架巧克力 51g', '51g', '横扫饥饿，做回自己', 4.50, 5.00, 'https://example.com/images/snickers_51g.jpg', 1, 1, 10),
(1006, '1420413387829231623', '6901234567893', '6901234567893', '乐事薯片原味 70g', '70g', '乐事薯片，乐享美味', 6.00, 7.00, 'https://example.com/images/lays_70g.jpg', 1, 1, 10),
(1007, '1420413387829231624', '6901234567894', '6901234567894', '旺旺雪饼 150g', '150g', '香脆可口的米果', 5.50, 6.50, 'https://example.com/images/want_want_150g.jpg', 1, 1, 10),
(1008, '1420413387829231625', '6901234567895', '6901234567895', '德芙巧克力 43g', '43g', '丝滑德芙，纵享丝滑', 8.50, 10.00, 'https://example.com/images/dove_43g.jpg', 1, 1, 10);

-- 2. 插入测试设备数据
INSERT INTO yt_devices (device_code, ext_code, device_name, device_model, net_status, ele_status, door_status, is_forbidden, location, address, merchant_id, company_id) VALUES
('DEV001', 'EXT001', '办公楼1F智能柜', 'HBL-2024-A1', 1, 1, 2, 0, '办公楼一楼大厅', '北京市朝阳区建国路1号办公楼', 1, 10),
('DEV002', 'EXT002', '食堂智能柜', 'HBL-2024-A1', 1, 1, 2, 0, '员工食堂', '北京市朝阳区建国路1号食堂', 1, 10),
('DEV003', 'EXT003', '宿舍楼智能柜', 'HBL-2024-B1', 1, 1, 2, 0, '宿舍楼1楼', '北京市朝阳区建国路1号宿舍楼', 1, 10);

-- 3. 插入设备商品关联数据
INSERT INTO yt_device_goods (device_code, goods_id, hbl_sku_id, slot_number, stock_quantity, max_capacity, status, company_id) VALUES
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
INSERT INTO yt_users (hbl_user_id, openid, nickname, avatar_url, phone, gender, register_source, company_id) VALUES
('YT_USER_001', 'oZKmq0tcsP zhMejIvmij43C2rAp0', '微信用户001', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example1.jpg', '13800138001', 1, 'MINIPROGRAM', 10),
('YT_USER_002', 'oZKmq0tcsP zhMejIvmij43C2rAp1', '微信用户002', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example2.jpg', '13800138002', 2, 'MINIPROGRAM', 10),
('YT_USER_003', 'oZKmq0tcsP zhMejIvmij43C2rAp2', '微信用户003', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example3.jpg', '13800138003', 1, 'MINIPROGRAM', 10);

-- 5. 插入测试事件数据
INSERT INTO yt_events (hbl_event_no, ext_event_no, device_code, user_id, event_type, pay_type, user_type, status, nonce_str, request_serial, time_stamp, company_id) VALUES
('YT20241218001', 'EXT20241218001', 'DEV001', 'YT_USER_001', 'OPEN_DOOR', 7, 1, 2, 'abc123def456', '20241218150001', '20241218150001000', 10),
('YT20241218002', 'EXT20241218002', 'DEV001', 'YT_USER_002', 'OPEN_DOOR', 7, 1, 2, 'def456ghi789', '20241218151001', '20241218151001000', 10),
('YT20241218003', 'EXT20241218003', 'DEV002', 'YT_USER_003', 'OPEN_DOOR', 6, 1, 2, 'ghi789jkl012', '20241218152001', '20241218152001000', 10);

-- 6. 插入测试订单数据
INSERT INTO yt_orders (order_code, hbl_order_no, event_id, device_code, user_id, order_amount, product_amount, promotion_amount, actual_amount, order_status, pay_status, pay_type, channel_type, trade_type, nonce_str, request_serial, time_stamp, company_id) VALUES
('ORD20241218001', 'YT_ORD_001', 1, 'DEV001', 'YT_USER_001', 7.00, 8.00, 1.00, 7.00, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'abc123def456', '20241218150001', '20241218150001000', 10),
('ORD20241218002', 'YT_ORD_002', 2, 'DEV001', 'YT_USER_002', 6.50, 7.00, 0.50, 6.50, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'def456ghi789', '20241218151001', '20241218151001000', 10),
('ORD20241218003', 'YT_ORD_003', 3, 'DEV002', 'YT_USER_003', 11.50, 13.00, 1.50, 11.50, 'COMPLETED', 'PAID', 6, 'alipay', 'PAY', 'ghi789jkl012', '20241218152001', '20241218152001000', 10),
('ORD20241218004', 'YT_ORD_004', NULL, 'DEV001', 'YT_USER_001', 5.50, 6.00, 0.50, 5.50, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'jkl012mno345', '20241218160001', '20241218160001000', 10),
('ORD20241218005', 'YT_ORD_005', NULL, 'DEV002', 'YT_USER_002', 9.00, 10.00, 1.00, 9.00, 'PAID', 'PAID', 7, 'wxpay', 'PAY', 'mno345pqr678', '20241218161001', '20241218161001000', 10);

-- 7. 插入测试订单商品明细数据
INSERT INTO yt_order_items (order_id, order_code, goods_id, hbl_sku_id, barcode, goods_name, goods_sku, unit_price, quantity, subtotal, actual_amount, goods_status) VALUES
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
INSERT INTO yt_recognition_results (event_id, hbl_event_no, ext_event_no, device_code, recog_code, anomaly_code, video_url, recognition_time, company_id) VALUES
(1, 'YT20241218001', 'EXT20241218001', 'DEV001', 1, 0, 'https://video.example.com/recognition/20241218001.mp4', '2024-12-18 15:00:30', 10),
(2, 'YT20241218002', 'EXT20241218002', 'DEV001', 1, 0, 'https://video.example.com/recognition/20241218002.mp4', '2024-12-18 15:10:30', 10),
(3, 'YT20241218003', 'EXT20241218003', 'DEV002', 1, 0, 'https://video.example.com/recognition/20241218003.mp4', '2024-12-18 15:20:30', 10);

-- 9. 插入识别商品明细测试数据
INSERT INTO yt_recognition_items (result_id, event_id, sku_id, barcode, ext_sku_code, goods_name, quantity, goods_status, unit_price) VALUES
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
UPDATE yt_users SET 
    total_order_count = (
        SELECT COUNT(*) FROM yt_orders WHERE user_id = yt_users.hbl_user_id AND order_status != 'CANCELLED'
    ),
    total_order_amount = (
        SELECT COALESCE(SUM(actual_amount), 0) FROM yt_orders WHERE user_id = yt_users.hbl_user_id AND order_status = 'COMPLETED'
    );

-- 11. 更新设备库存信息（模拟商品被购买后的库存变化）
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1001;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1005;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1002;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1003;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 2 WHERE device_code = 'DEV002' AND goods_id = 1001;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV002' AND goods_id = 1007;

-- ========================================
-- 新增测试数据 - 用于测试新增的API接口
-- ========================================

-- 12. 插入更多测试用户数据
INSERT INTO yt_users (hbl_user_id, openid, nickname, avatar_url, phone, gender, register_source, company_id) VALUES
('YT_USER_004', 'oZKmq0tcsP zhMejIvmij43C2rAp3', '微信用户004', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example4.jpg', '13800138004', 2, 'MINIPROGRAM', 10),
('YT_USER_005', 'oZKmq0tcsP zhMejIvmij43C2rAp4', '微信用户005', 'https://thirdwx.qlogo.cn/mmopen/vi_32/example5.jpg', '13800138005', 1, 'MINIPROGRAM', 10);

-- 13. 插入更多测试事件数据
INSERT INTO yt_events (hbl_event_no, ext_event_no, device_code, user_id, event_type, pay_type, user_type, status, nonce_str, request_serial, time_stamp, company_id) VALUES
('YT20241218004', 'EXT20241218004', 'DEV001', 'YT_USER_004', 'OPEN_DOOR', 7, 1, 2, 'pqr678stu901', '20241218170001', '20241218170001000', 10),
('YT20241218005', 'EXT20241218005', 'DEV002', 'YT_USER_005', 'OPEN_DOOR', 6, 1, 2, 'stu901vwx234', '20241218171001', '20241218171001000', 10),
('YT20241218006', 'EXT20241218006', 'DEV003', 'YT_USER_001', 'OPEN_DOOR', 7, 1, 2, 'vwx234yza567', '20241218172001', '20241218172001000', 10);

-- 14. 插入更多测试订单数据 - 重点测试"待支付"和"售后退款"状态
INSERT INTO yt_orders (order_code, hbl_order_no, event_id, device_code, user_id, order_amount, product_amount, promotion_amount, actual_amount, order_status, pay_status, pay_type, channel_type, trade_type, nonce_str, request_serial, time_stamp, company_id) VALUES
-- 待支付订单 - 用户001
('ORD20241218006', 'YT_ORD_006', NULL, 'DEV001', 'YT_USER_001', 12.50, 14.00, 1.50, 12.50, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'yza567bcd890', '20241218173001', '20241218173001000', 10),
('ORD20241218007', 'YT_ORD_007', NULL, 'DEV002', 'YT_USER_001', 8.00, 9.00, 1.00, 8.00, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'bcd890efg123', '20241218174001', '20241218174001000', 10),
('ORD20241218008', 'YT_ORD_008', NULL, 'DEV003', 'YT_USER_001', 15.50, 17.00, 1.50, 15.50, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'efg123hij456', '20241218175001', '20241218175001000', 10),

-- 待支付订单 - 用户002
('ORD20241218009', 'YT_ORD_009', NULL, 'DEV001', 'YT_USER_002', 6.00, 7.00, 1.00, 6.00, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'hij456klm789', '20241218180001', '20241218180001000', 10),
('ORD20241218010', 'YT_ORD_010', NULL, 'DEV002', 'YT_USER_002', 10.50, 12.00, 1.50, 10.50, 'PENDING', 'UNPAID', 6, 'alipay', 'PAY', 'klm789nop012', '20241218181001', '20241218181001000', 10),

-- 待支付订单 - 用户003
('ORD20241218011', 'YT_ORD_011', NULL, 'DEV003', 'YT_USER_003', 7.50, 8.50, 1.00, 7.50, 'PENDING', 'UNPAID', 7, 'wxpay', 'PAY', 'nop012qrs345', '20241218182001', '20241218182001000', 10),

-- 售后退款订单 - 用户001
('ORD20241218012', 'YT_ORD_012', NULL, 'DEV001', 'YT_USER_001', 9.00, 10.00, 1.00, 9.00, 'REFUNDED', 'REFUNDED', 7, 'wxpay', 'REFUND', 'qrs345tuv678', '20241218183001', '20241218183001000', 10),
('ORD20241218013', 'YT_ORD_013', NULL, 'DEV002', 'YT_USER_001', 13.50, 15.00, 1.50, 13.50, 'REFUNDED', 'REFUNDED', 6, 'alipay', 'REFUND', 'tuv678wxy901', '20241218184001', '20241218184001000', 10),

-- 售后退款订单 - 用户002
('ORD20241218014', 'YT_ORD_014', NULL, 'DEV003', 'YT_USER_002', 5.50, 6.00, 0.50, 5.50, 'REFUNDED', 'REFUNDED', 7, 'wxpay', 'REFUND', 'wxy901zab234', '20241218185001', '20241218185001000', 10),

-- 售后退款订单 - 用户003
('ORD20241218015', 'YT_ORD_015', NULL, 'DEV001', 'YT_USER_003', 11.00, 12.50, 1.50, 11.00, 'REFUNDED', 'REFUNDED', 7, 'wxpay', 'REFUND', 'zab234cde567', '20241218190001', '20241218190001000', 10),

-- 已完成订单 - 用户004
('ORD20241218016', 'YT_ORD_016', 4, 'DEV001', 'YT_USER_004', 16.50, 18.00, 1.50, 16.50, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'cde567fgh890', '20241218191001', '20241218191001000', 10),

-- 已完成订单 - 用户005
('ORD20241218017', 'YT_ORD_017', 5, 'DEV002', 'YT_USER_005', 14.00, 16.00, 2.00, 14.00, 'COMPLETED', 'PAID', 6, 'alipay', 'PAY', 'fgh890ijk123', '20241218192001', '20241218192001000', 10),

-- 已完成订单 - 用户001
('ORD20241218018', 'YT_ORD_018', 6, 'DEV003', 'YT_USER_001', 20.50, 23.00, 2.50, 20.50, 'COMPLETED', 'PAID', 7, 'wxpay', 'PAY', 'ijk123lmn456', '20241218193001', '20241218193001000', 10);

-- 15. 插入更多测试订单商品明细数据
INSERT INTO yt_order_items (order_id, order_code, goods_id, hbl_sku_id, barcode, goods_name, goods_sku, unit_price, quantity, subtotal, actual_amount, goods_status) VALUES
-- 订单6的商品 - 待支付
(6, 'ORD20241218006', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 2, 7.00, 6.00, 1),
(6, 'ORD20241218006', 1005, '1420413387829231622', '6901234567892', '士力架巧克力 51g', '51g', 4.50, 1, 4.50, 4.50, 1),
(6, 'ORD20241218006', 1008, '1420413387829231625', '6901234567895', '德芙巧克力 43g', '43g', 8.50, 1, 8.50, 2.00, 1),

-- 订单7的商品 - 待支付
(7, 'ORD20241218007', 1002, '1420413387829231619', '6902827100069', '百事可乐 330ml', '330ml', 3.50, 1, 3.50, 3.50, 1),
(7, 'ORD20241218007', 1006, '1420413387829231623', '6901234567893', '乐事薯片原味 70g', '70g', 6.00, 1, 6.00, 4.50, 1),

-- 订单8的商品 - 待支付
(8, 'ORD20241218008', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 3, 10.50, 9.00, 1),
(8, 'ORD20241218008', 1007, '1420413387829231624', '6901234567894', '旺旺雪饼 150g', '150g', 5.50, 1, 5.50, 6.50, 1),

-- 订单9的商品 - 待支付
(9, 'ORD20241218009', 1003, '1420413387829231620', '6901234567890', '康师傅绿茶 500ml', '500ml', 3.00, 1, 3.00, 3.00, 1),
(9, 'ORD20241218009', 1004, '1420413387829231621', '6901234567891', '农夫山泉 550ml', '550ml', 2.00, 1, 2.00, 3.00, 1),

-- 订单10的商品 - 待支付
(10, 'ORD20241218010', 1005, '1420413387829231622', '6901234567892', '士力架巧克力 51g', '51g', 4.50, 2, 9.00, 7.50, 1),
(10, 'ORD20241218010', 1008, '1420413387829231625', '6901234567895', '德芙巧克力 43g', '43g', 8.50, 1, 8.50, 3.00, 1),

-- 订单11的商品 - 待支付
(11, 'ORD20241218011', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 1, 3.50, 3.50, 1),
(11, 'ORD20241218011', 1004, '1420413387829231621', '6901234567891', '农夫山泉 550ml', '550ml', 2.00, 2, 4.00, 4.00, 1),

-- 订单12的商品 - 售后退款
(12, 'ORD20241218012', 1002, '1420413387829231619', '6902827100069', '百事可乐 330ml', '330ml', 3.50, 2, 7.00, 6.00, 1),
(12, 'ORD20241218012', 1006, '1420413387829231623', '6901234567893', '乐事薯片原味 70g', '70g', 6.00, 1, 6.00, 3.00, 1),

-- 订单13的商品 - 售后退款
(13, 'ORD20241218013', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 3, 10.50, 9.00, 1),
(13, 'ORD20241218013', 1007, '1420413387829231624', '6901234567894', '旺旺雪饼 150g', '150g', 5.50, 1, 5.50, 4.50, 1),

-- 订单14的商品 - 售后退款
(14, 'ORD20241218014', 1003, '1420413387829231620', '6901234567890', '康师傅绿茶 500ml', '500ml', 3.00, 1, 3.00, 3.00, 1),
(14, 'ORD20241218014', 1005, '1420413387829231622', '6901234567892', '士力架巧克力 51g', '51g', 4.50, 1, 4.50, 2.50, 1),

-- 订单15的商品 - 售后退款
(15, 'ORD20241218015', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 2, 7.00, 6.00, 1),
(15, 'ORD20241218015', 1008, '1420413387829231625', '6901234567895', '德芙巧克力 43g', '43g', 8.50, 1, 8.50, 5.00, 1),

-- 订单16的商品 - 已完成
(16, 'ORD20241218016', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 3, 10.50, 9.00, 1),
(16, 'ORD20241218016', 1005, '1420413387829231622', '6901234567892', '士力架巧克力 51g', '51g', 4.50, 2, 9.00, 7.50, 1),

-- 订单17的商品 - 已完成
(17, 'ORD20241218017', 1002, '1420413387829231619', '6902827100069', '百事可乐 330ml', '330ml', 3.50, 2, 7.00, 6.00, 1),
(17, 'ORD20241218017', 1007, '1420413387829231624', '6901234567894', '旺旺雪饼 150g', '150g', 5.50, 1, 5.50, 8.00, 1),

-- 订单18的商品 - 已完成
(18, 'ORD20241218018', 1001, '1420413387829231618', '6943878403365', '可口可乐 330ml', '330ml', 3.50, 4, 14.00, 12.00, 1),
(18, 'ORD20241218018', 1008, '1420413387829231625', '6901234567895', '德芙巧克力 43g', '43g', 8.50, 1, 8.50, 8.50, 1);

-- 16. 更新用户统计信息（包含新增订单）
UPDATE yt_users SET 
    total_order_count = (
        SELECT COUNT(*) FROM yt_orders WHERE user_id = yt_users.hbl_user_id AND order_status != 'CANCELLED'
    ),
    total_order_amount = (
        SELECT COALESCE(SUM(actual_amount), 0) FROM yt_orders WHERE user_id = yt_users.hbl_user_id AND order_status = 'COMPLETED'
    );

-- 17. 更新设备库存信息（模拟新增订单的库存变化）
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 2 WHERE device_code = 'DEV001' AND goods_id = 1001;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1005;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV001' AND goods_id = 1008;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV002' AND goods_id = 1002;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV002' AND goods_id = 1006;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 3 WHERE device_code = 'DEV003' AND goods_id = 1001;
UPDATE yt_device_goods SET stock_quantity = stock_quantity - 1 WHERE device_code = 'DEV003' AND goods_id = 1007;

-- ========================================
-- 测试数据统计信息
-- ========================================
/*
新增测试数据总结：

1. 待支付订单 (PENDING):
   - YT_USER_001: 4个订单 (ORD20241218004, ORD20241218006, ORD20241218007, ORD20241218008)
   - YT_USER_002: 2个订单 (ORD20241218009, ORD20241218010)
   - YT_USER_003: 1个订单 (ORD20241218011)
   总计: 7个待支付订单

2. 售后退款订单 (REFUNDED):
   - YT_USER_001: 2个订单 (ORD20241218012, ORD20241218013)
   - YT_USER_002: 1个订单 (ORD20241218014)
   - YT_USER_003: 1个订单 (ORD20241218015)
   总计: 4个售后退款订单

3. 已完成订单 (COMPLETED):
   - YT_USER_001: 2个订单 (ORD20241218001, ORD20241218018)
   - YT_USER_002: 1个订单 (ORD20241218002)
   - YT_USER_003: 1个订单 (ORD20241218003)
   - YT_USER_004: 1个订单 (ORD20241218016)
   - YT_USER_005: 1个订单 (ORD20241218017)
   总计: 6个已完成订单

4. 已支付订单 (PAID):
   - YT_USER_002: 1个订单 (ORD20241218005)
   总计: 1个已支付订单

这些测试数据可以充分测试以下API接口：
- GET /api/yitong/orders/pending-payment - 待支付订单查询
- GET /api/yitong/orders/refund - 售后退款订单查询
- GET /api/yitong/orders/pending-payment/count - 待支付订单数量统计
- GET /api/yitong/orders/refund/count - 售后退款订单数量统计
*/
