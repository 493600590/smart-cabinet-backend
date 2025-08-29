# 微信小程序订单API接口文档

## 概述
本文档描述了微信小程序订单功能的后端API接口，包括全部订单、待支付订单、售后退款订单等功能的接口说明。

## 基础信息
- **基础URL**: `http://localhost:8080/api/yitong/orders`
- **请求格式**: JSON
- **响应格式**: JSON
- **字符编码**: UTF-8
- **认证方式**: 通过userId参数进行用户身份验证

## 接口列表

### 1. 查询用户全部订单（分页）
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

### 2. 查询用户待支付订单（分页）
**接口地址**: `GET /yitong/orders/pending-payment`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 页大小，默认10 |

**请求示例**:
```
GET /api/yitong/orders/pending-payment?userId=YT_USER_001&pageNum=1&pageSize=10
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询待支付订单成功",
    "data": {
        "records": [
            {
                "orderId": 2,
                "orderCode": "ORD20241218002",
                "deviceCode": "DEV002",
                "userId": "YT_USER_001",
                "orderAmount": 12.50,
                "actualAmount": 12.50,
                "orderStatus": "PENDING",
                "payStatus": "UNPAID",
                "createdAt": "2024-12-18 16:30:00"
            }
        ],
        "total": 1,
        "size": 10,
        "current": 1,
        "pages": 1
    }
}
```

### 3. 查询用户售后退款订单（分页）
**接口地址**: `GET /yitong/orders/refund`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 页大小，默认10 |

**请求示例**:
```
GET /api/yitong/orders/refund?userId=YT_USER_001&pageNum=1&pageSize=10
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询售后退款订单成功",
    "data": {
        "records": [
            {
                "orderId": 3,
                "orderCode": "ORD20241218003",
                "deviceCode": "DEV003",
                "userId": "YT_USER_001",
                "orderAmount": 8.00,
                "actualAmount": 8.00,
                "orderStatus": "REFUNDED",
                "payStatus": "REFUNDED",
                "createdAt": "2024-12-18 14:20:00"
            }
        ],
        "total": 1,
        "size": 10,
        "current": 1,
        "pages": 1
    }
}
```

### 4. 查询待支付订单数量
**接口地址**: `GET /yitong/orders/pending-payment/count`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |

**请求示例**:
```
GET /api/yitong/orders/pending-payment/count?userId=YT_USER_001
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询待支付订单数量成功",
    "data": 2
}
```

### 5. 查询售后退款订单数量
**接口地址**: `GET /yitong/orders/refund/count`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| userId | String | 是 | 用户ID |

**请求示例**:
```
GET /api/yitong/orders/refund/count?userId=YT_USER_001
```

**响应示例**:
```json
{
    "code": 200,
    "message": "查询售后退款订单数量成功",
    "data": 1
}
```

### 6. 查询订单详情
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
        "deviceCode": "DEV001",
        "userId": "YT_USER_001",
        "orderAmount": 7.00,
        "actualAmount": 7.00,
        "orderStatus": "COMPLETED",
        "payStatus": "PAID",
        "createdAt": "2024-12-18 15:00:01",
        "orderItems": [
            {
                "itemId": 1,
                "goodsId": 1001,
                "goodsName": "可口可乐 330ml",
                "unitPrice": 3.50,
                "quantity": 1,
                "subtotal": 3.50
            }
        ]
    }
}
```

## 订单状态说明

### 订单状态 (orderStatus)
- `PENDING` - 待支付
- `PAID` - 已支付
- `COMPLETED` - 已完成
- `CANCELLED` - 已取消
- `REFUNDED` - 已退款

### 支付状态 (payStatus)
- `UNPAID` - 未支付
- `PAID` - 已支付
- `REFUNDING` - 退款中
- `REFUNDED` - 已退款

## 微信小程序集成说明

### 1. 订单列表页面
```javascript
// 获取全部订单
wx.request({
  url: 'http://localhost:8080/api/yitong/orders',
  data: {
    userId: 'YT_USER_001',
    pageNum: 1,
    pageSize: 10
  },
  success: function(res) {
    if (res.data.code === 200) {
      this.setData({
        allOrders: res.data.data.records
      });
    }
  }
});

// 获取待支付订单
wx.request({
  url: 'http://localhost:8080/api/yitong/orders/pending-payment',
  data: {
    userId: 'YT_USER_001',
    pageNum: 1,
    pageSize: 10
  },
  success: function(res) {
    if (res.data.code === 200) {
      this.setData({
        pendingOrders: res.data.data.records
      });
    }
  }
});

// 获取售后退款订单
wx.request({
  url: 'http://localhost:8080/api/yitong/orders/refund',
  data: {
    userId: 'YT_USER_001',
    pageNum: 1,
    pageSize: 10
  },
  success: function(res) {
    if (res.data.code === 200) {
      this.setData({
        refundOrders: res.data.data.records
      });
    }
  }
});
```

### 2. 订单数量徽标
```javascript
// 获取待支付订单数量
wx.request({
  url: 'http://localhost:8080/api/yitong/orders/pending-payment/count',
  data: {
    userId: 'YT_USER_001'
  },
  success: function(res) {
    if (res.data.code === 200) {
      this.setData({
        pendingCount: res.data.data
      });
    }
  }
});

// 获取售后退款订单数量
wx.request({
  url: 'http://localhost:8080/api/yitong/orders/refund/count',
  data: {
    userId: 'YT_USER_001'
  },
  success: function(res) {
    if (res.data.code === 200) {
      this.setData({
        refundCount: res.data.data
      });
    }
  }
});
```

## 错误码说明

| 错误码 | 描述 |
|--------|------|
| 200 | 请求成功 |
| 400 | 请求参数错误 |
| 401 | 未授权访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 注意事项

1. **用户身份验证**: 所有接口都需要通过userId参数进行用户身份验证
2. **分页参数**: pageNum从1开始，pageSize建议设置为10-20
3. **订单状态**: 根据业务需求，订单状态可能会动态变化
4. **数据一致性**: 订单数据与商品库存数据保持实时同步
5. **性能优化**: 建议在微信小程序端实现数据缓存，减少重复请求

## 更新日志

- **2024-12-18**: 新增待支付订单和售后退款订单查询接口
- **2024-12-18**: 新增订单数量统计接口，支持徽标显示
- **2024-12-18**: 完善API文档，增加微信小程序集成示例 