# 易通无人柜订单API接口文档

## 概述
本文档描述了易通无人柜微信小程序订单功能的后端API接口，基于嗨便利2.0开放接口文档设计，适配易通无人柜系统。

## 基础信息
- **基础URL**: `http://localhost:8080/api/yitong/orders`
- **请求格式**: JSON
- **响应格式**: JSON
- **字符编码**: UTF-8

## 接口列表

### 1. 查询用户所有订单（分页）
**接口地址**: `GET /yitong/orders`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 页大小，默认10 |

**请求示例**:
```
GET /api/yitong/orders?userId=YT_USER_001&pageNum=1&pageSize=10
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询用户订单成功",
    "data": {
        "records": [
            {
                "orderId": 1,
                "orderCode": "ORD20241218001",
                "yitongOrderNo": "YT_ORD_001",
                "deviceCode": "DEV001",
                "userId": "YT_USER_001",
                "orderAmount": 7.00,
                "actualAmount": 7.00,
                "orderStatus": "COMPLETED",
                "payStatus": "PAID",
                "payType": 7,
                "channelType": "wxpay",
                "createdAt": "2024-12-18 15:00:01"
            }
        ],
        "total": 5,
        "size": 10,
        "current": 1,
        "pages": 1
    }
}
```

### 2. 根据订单状态查询订单（分页）
**接口地址**: `GET /yitong/orders/status`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |
| orderStatus | String | 是 | 订单状态 (PENDING/PAID/COMPLETED/CANCELLED/REFUNDED) |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 页大小，默认10 |

**请求示例**:
```
GET /api/yitong/orders/status?userId=YT_USER_001&orderStatus=COMPLETED&pageNum=1&pageSize=10
```

### 3. 查询订单详情
**接口地址**: `GET /yitong/orders/detail/{orderCode}`

**路径参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| orderCode | String | 是 | 订单编号 |

**请求示例**:
```
GET /api/yitong/orders/detail/ORD20241218001
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询订单详情成功",
    "data": {
        "orderId": 1,
        "orderCode": "ORD20241218001",
        "yitongOrderNo": "YT_ORD_001",
        "deviceCode": "DEV001",
        "userId": "YT_USER_001",
        "orderAmount": 7.00,
        "actualAmount": 7.00,
        "orderStatus": "COMPLETED",
        "payStatus": "PAID",
        "payType": 7,
        "channelType": "wxpay",
        "createdAt": "2024-12-18 15:00:01",
        "orderItems": [
            {
                "itemId": 1,
                "goodsId": 1001,
                "goodsName": "可口可乐 330ml",
                "goodsSku": "330ml",
                "unitPrice": 3.50,
                "quantity": 1,
                "subtotal": 3.50,
                "actualAmount": 3.50,
                "goodsImage": "https://example.com/images/coca_cola_330.jpg"
            },
            {
                "itemId": 2,
                "goodsId": 1005,
                "goodsName": "士力架巧克力 51g",
                "goodsSku": "51g",
                "unitPrice": 4.50,
                "quantity": 1,
                "subtotal": 4.50,
                "actualAmount": 3.50,
                "goodsImage": "https://example.com/images/snickers_51g.jpg"
            }
        ]
    }
}
```

### 4. 查询用户订单统计
**接口地址**: `GET /yitong/orders/statistics`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |

**请求示例**:
```
GET /api/yitong/orders/statistics?userId=YT_USER_001
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询订单统计成功",
    "data": {
        "statusCounts": [
            {"order_status": "COMPLETED", "count": 3},
            {"order_status": "PENDING", "count": 1},
            {"order_status": "PAID", "count": 1}
        ],
        "recentOrders": [
            {
                "orderId": 4,
                "orderCode": "ORD20241218004",
                "orderStatus": "PENDING",
                "actualAmount": 5.50,
                "createdAt": "2024-12-18 16:00:01"
            }
        ]
    }
}
```

### 5. 查询用户最近订单
**接口地址**: `GET /yitong/orders/recent`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |
| limit | Integer | 否 | 返回数量限制，默认5 |

**请求示例**:
```
GET /api/yitong/orders/recent?userId=YT_USER_001&limit=5
```

### 6. 查询订单商品明细
**接口地址**: `GET /yitong/orders/{orderCode}/items`

**路径参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| orderCode | String | 是 | 订单编号 |

**请求示例**:
```
GET /api/yitong/orders/ORD20241218001/items
```

## 微信小程序使用场景

### 1. 订单列表页面
- 使用接口1查询用户所有订单
- 可以按订单状态筛选（使用接口2）
- 显示订单基本信息：订单号、金额、状态、时间

### 2. 订单详情页面
- 使用接口3查询订单详情
- 显示订单完整信息和商品明细
- 展示设备位置、支付方式等信息

### 3. 首页订单展示
- 使用接口5查询最近订单
- 显示用户最近的购买记录

### 4. 订单统计页面
- 使用接口4查询订单统计
- 展示各状态订单数量分布
- 显示用户购买行为分析

## 订单状态说明
- **PENDING**: 待支付
- **PAID**: 已支付
- **COMPLETED**: 已完成
- **CANCELLED**: 已取消
- **REFUNDED**: 已退款

## 支付类型说明
- **0**: 未知
- **5**: 支付宝扫码
- **6**: 支付宝刷脸
- **7**: 微信扫码
- **8**: 微信刷脸

## 注意事项
1. 所有接口都需要用户身份验证
2. 分页查询默认按创建时间倒序排列
3. 订单详情包含商品快照信息，避免商品信息变更影响历史订单
4. 预留字段可用于后续功能扩展
