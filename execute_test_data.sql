-- 执行易通无人柜订单系统测试数据
-- 用于测试新增的API接口

USE smart_cabinet;

-- 清空现有测试数据（可选，谨慎使用）
-- DELETE FROM hibianli_order_items WHERE order_id > 5;
-- DELETE FROM hibianli_orders WHERE order_id > 5;
-- DELETE FROM hibianli_events WHERE event_id > 3;
-- DELETE FROM hibianli_users WHERE hbl_user_id IN ('YT_USER_004', 'YT_USER_005');

-- 执行测试数据插入
SOURCE src/main/resources/sql/yt_test_data.sql;

-- 验证数据插入结果
SELECT 
    '订单状态统计' as info,
    order_status,
    COUNT(*) as count
FROM hibianli_orders 
WHERE deleted = 0 
GROUP BY order_status
ORDER BY order_status;

SELECT 
    '用户订单数量统计' as info,
    user_id,
    COUNT(*) as total_orders,
    SUM(CASE WHEN order_status = 'PENDING' THEN 1 ELSE 0 END) as pending_orders,
    SUM(CASE WHEN order_status = 'REFUNDED' THEN 1 ELSE 0 END) as refunded_orders,
    SUM(CASE WHEN order_status = 'COMPLETED' THEN 1 ELSE 0 END) as completed_orders
FROM hibianli_orders 
WHERE deleted = 0 
GROUP BY user_id
ORDER BY user_id;

-- 测试待支付订单查询
SELECT 
    '待支付订单测试' as info,
    COUNT(*) as total_pending
FROM hibianli_orders 
WHERE order_status = 'PENDING' AND deleted = 0;

-- 测试售后退款订单查询
SELECT 
    '售后退款订单测试' as info,
    COUNT(*) as total_refunded
FROM hibianli_orders 
WHERE order_status = 'REFUNDED' AND deleted = 0;

-- 显示前5个待支付订单
SELECT 
    '前5个待支付订单' as info,
    order_id,
    order_code,
    user_id,
    order_amount,
    actual_amount,
    order_status,
    pay_status,
    created_at
FROM hibianli_orders 
WHERE order_status = 'PENDING' AND deleted = 0
ORDER BY created_at DESC
LIMIT 5;

-- 显示前5个售后退款订单
SELECT 
    '前5个售后退款订单' as info,
    order_id,
    order_code,
    user_id,
    order_amount,
    actual_amount,
    order_status,
    pay_status,
    created_at
FROM hibianli_orders 
WHERE order_status = 'REFUNDED' AND deleted = 0
ORDER BY created_at DESC
LIMIT 5; 