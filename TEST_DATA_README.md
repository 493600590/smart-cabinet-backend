# 易通无人柜订单系统测试数据说明

## 📋 概述

本文档说明了为新增的易通无人柜订单API接口生成的测试数据，包括"待支付"和"售后退款"订单的测试用例。

## 🎯 新增的API接口

### 1. 待支付订单查询接口
- **接口地址**: `GET /api/yitong/orders/pending-payment`
- **功能**: 查询用户的待支付订单列表
- **参数**: `userId`, `pageNum`, `pageSize`

### 2. 售后退款订单查询接口
- **接口地址**: `GET /api/yitong/orders/refund`
- **功能**: 查询用户的售后退款订单列表
- **参数**: `userId`, `pageNum`, `pageSize`

### 3. 待支付订单数量统计
- **接口地址**: `GET /api/yitong/orders/pending-payment/count`
- **功能**: 统计用户的待支付订单数量
- **参数**: `userId`

### 4. 售后退款订单数量统计
- **接口地址**: `GET /api/yitong/orders/refund/count`
- **功能**: 统计用户的售后退款订单数量
- **参数**: `userId`

## 📊 测试数据统计

### 订单状态分布
| 订单状态 | 数量 | 说明 |
|---------|------|------|
| PENDING (待支付) | 7个 | 用于测试待支付相关接口 |
| REFUNDED (售后退款) | 4个 | 用于测试售后退款相关接口 |
| COMPLETED (已完成) | 6个 | 用于测试全部订单接口 |
| PAID (已支付) | 1个 | 用于测试已支付订单 |

### 用户订单分布
| 用户ID | 待支付订单 | 售后退款订单 | 已完成订单 | 总计 |
|--------|------------|--------------|------------|------|
| YT_USER_001 | 4个 | 2个 | 2个 | 8个 |
| YT_USER_002 | 2个 | 1个 | 1个 | 4个 |
| YT_USER_003 | 1个 | 1个 | 1个 | 3个 |
| YT_USER_004 | 0个 | 0个 | 1个 | 1个 |
| YT_USER_005 | 0个 | 0个 | 1个 | 1个 |

## 🚀 使用方法

### 1. 执行测试数据插入

#### 方法1: 使用MySQL命令行
```bash
# 连接到数据库
mysql -u username -p smart_cabinet

# 执行测试数据脚本
source src/main/resources/sql/yt_test_data.sql;
```

#### 方法2: 使用执行脚本
```bash
# 执行验证脚本
mysql -u username -p smart_cabinet < execute_test_data.sql
```

### 2. 验证数据插入

执行以下SQL查询验证数据是否正确插入：

```sql
-- 查看订单状态统计
SELECT 
    order_status,
    COUNT(*) as count
FROM hibianli_orders 
WHERE deleted = 0 
GROUP BY order_status
ORDER BY order_status;

-- 查看用户订单数量统计
SELECT 
    user_id,
    COUNT(*) as total_orders,
    SUM(CASE WHEN order_status = 'PENDING' THEN 1 ELSE 0 END) as pending_orders,
    SUM(CASE WHEN order_status = 'REFUNDED' THEN 1 ELSE 0 END) as refunded_orders,
    SUM(CASE WHEN order_status = 'COMPLETED' THEN 1 ELSE 0 END) as completed_orders
FROM hibianli_orders 
WHERE deleted = 0 
GROUP BY user_id
ORDER BY user_id;
```

### 3. 运行API测试

使用Python脚本测试新增的API接口：

```bash
python test_new_api_with_data.py
```

## 📝 测试数据详情

### 待支付订单示例
- **ORD20241218006**: 用户001，金额¥12.50，包含3种商品
- **ORD20241218007**: 用户001，金额¥8.00，包含2种商品
- **ORD20241218008**: 用户001，金额¥15.50，包含2种商品
- **ORD20241218009**: 用户002，金额¥6.00，包含2种商品
- **ORD20241218010**: 用户002，金额¥10.50，包含2种商品
- **ORD20241218011**: 用户003，金额¥7.50，包含2种商品

### 售后退款订单示例
- **ORD20241218012**: 用户001，金额¥9.00，包含2种商品
- **ORD20241218013**: 用户001，金额¥13.50，包含2种商品
- **ORD20241218014**: 用户002，金额¥5.50，包含2种商品
- **ORD20241218015**: 用户003，金额¥11.00，包含2种商品

## 🔧 数据清理

如果需要清理测试数据，可以使用以下SQL语句：

```sql
-- 清理新增的订单商品明细
DELETE FROM hibianli_order_items WHERE order_id > 5;

-- 清理新增的订单
DELETE FROM hibianli_orders WHERE order_id > 5;

-- 清理新增的事件
DELETE FROM hibianli_events WHERE event_id > 3;

-- 清理新增的用户
DELETE FROM hibianli_users WHERE hbl_user_id IN ('YT_USER_004', 'YT_USER_005');
```

## 📱 微信小程序集成示例

```javascript
// 查询待支付订单
wx.request({
  url: 'http://localhost:8000/api/yitong/orders/pending-payment',
  method: 'GET',
  data: {
    userId: 'YT_USER_001',
    pageNum: 1,
    pageSize: 10
  },
  success: function(res) {
    if (res.data.code === 200) {
      const orders = res.data.data.records;
      console.log('待支付订单:', orders);
    }
  }
});

// 查询售后退款订单
wx.request({
  url: 'http://localhost:8000/api/yitong/orders/refund',
  method: 'GET',
  data: {
    userId: 'YT_USER_001',
    pageNum: 1,
    pageSize: 10
  },
  success: function(res) {
    if (res.data.code === 200) {
      const orders = res.data.data.records;
      console.log('售后退款订单:', orders);
    }
  }
});

// 获取待支付订单数量
wx.request({
  url: 'http://localhost:8000/api/yitong/orders/pending-payment/count',
  method: 'GET',
  data: { userId: 'YT_USER_001' },
  success: function(res) {
    if (res.data.code === 200) {
      const count = res.data.data;
      console.log('待支付订单数量:', count);
    }
  }
});
```

## ✅ 验证清单

- [ ] 测试数据成功插入数据库
- [ ] 待支付订单数量统计接口正常
- [ ] 售后退款订单数量统计接口正常
- [ ] 待支付订单列表查询接口正常
- [ ] 售后退款订单列表查询接口正常
- [ ] 分页功能正常工作
- [ ] 微信小程序可以正常调用接口

## 🆘 常见问题

### 1. 数据插入失败
- 检查数据库连接是否正常
- 确认数据库表结构是否正确
- 检查SQL语法是否有误

### 2. API接口返回404
- 确认应用程序正在运行
- 检查接口路径是否正确
- 验证Spring Boot配置

### 3. 分页功能异常
- 检查分页参数是否正确
- 验证数据库查询逻辑
- 确认MyBatis-Plus配置

## 📞 技术支持

如有问题，请检查：
1. 应用程序日志
2. 数据库连接状态
3. API接口文档
4. 测试脚本输出 