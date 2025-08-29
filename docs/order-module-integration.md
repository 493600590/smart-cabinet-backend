# 订单模块联调指南 (WeChat 小程序 / Web 前端)

> 本文档用于指导前端与后端进行订单模块联调与自测，包含环境、接口、示例与常见问题。

## 一、环境与访问地址
- **后端已启动**：端口 `8000`，Context Path `/api`
- **Swagger (本机)**：`http://localhost:8000/api/swagger-ui/index.html`
- **Swagger (局域网)**：`http://172.30.17.28:8000/api/swagger-ui/index.html`
- **Base URL**：`http://172.30.17.28:8000/api`

> 如需公网联调，请配置公网 HTTPS 域名并在小程序后台添加为 request 合法域名。

## 二、接口清单（yitong/orders）
- 全部订单：`GET /yitong/orders/all`
- 待支付列表：`GET /yitong/orders/pending-payment`
- 售后退款列表：`GET /yitong/orders/refund`
- 待支付数量统计：`GET /yitong/orders/pending-payment/count`
- 售后退款数量统计：`GET /yitong/orders/refund/count`

### 通用查询参数
- `userId` (String) 必填：用户唯一标识（示例：`YT_USER_001`）
- `pageNum` (Integer) 选填：页码，默认 `1`
- `pageSize` (Integer) 选填：每页条数，默认 `10`

### 返回体约定
- 统一 `Result<T>` 包装：`code`、`msg`、`data`
- 列表接口 `data` 为分页对象，包含 `total`、`current`、`size`、`records` 等

## 三、快速自测（cURL 示例）
```bash
# Swagger 可用性
curl -I "http://localhost:8000/api/swagger-ui/index.html"

# 全部订单
curl "http://localhost:8000/api/yitong/orders/all?userId=YT_USER_001&pageNum=1&pageSize=10"

# 待支付列表
curl "http://localhost:8000/api/yitong/orders/pending-payment?userId=YT_USER_001&pageNum=1&pageSize=10"

# 退款列表
curl "http://localhost:8000/api/yitong/orders/refund?userId=YT_USER_001&pageNum=1&pageSize=10"

# 待支付数量
curl "http://localhost:8000/api/yitong/orders/pending-payment/count?userId=YT_USER_001"

# 退款数量
curl "http://localhost:8000/api/yitong/orders/refund/count?userId=YT_USER_001"
```

## 四、字段映射建议
- `orderCode`：订单号
- `orderStatus`：订单状态（`PENDING`、`PAID`、`COMPLETED`、`REFUNDED`）
- `totalAmount`：订单总金额（分或元，按后端定义展示）
- `createdAt`：创建时间（建议前端格式化）
- `items`：商品明细（如存在 `hibianli_order_items` 关联）

## 五、WeChat 小程序接入要点
- 开发阶段可在微信开发者工具勾选「忽略 request 合法域名校验」先行联调
- 真机/预发：需使用 HTTPS 域名并在小程序后台配置为合法域名
- 统一管理 Base URL（示例：`http://172.30.17.28:8000/api`）
- GET 请求携带 `userId`、`pageNum`、`pageSize`
- 如果后端开启 JWT，需在请求头添加 `Authorization: Bearer <token>`

### WeChat 示例
```javascript
wx.request({
  url: 'http://172.30.17.28:8000/api/yitong/orders/pending-payment',
  method: 'GET',
  data: { userId: 'YT_USER_001', pageNum: 1, pageSize: 10 },
  header: {
    // 若开启鉴权，取消注释：
    // 'Authorization': 'Bearer ' + token
  },
  success(res) {
    console.log('pending payment:', res.data);
  },
  fail(err) {
    console.error(err);
  }
});
```

## 六、联调步骤建议
1. 通过 Swagger 验证 4 个接口均可 200 返回
2. 前端先接入「全部订单」列表，核对分页与字段映射
3. 接入「待支付」与「售后退款」列表，验证筛选正确
4. 接入两个数量统计接口，用于 Tab 角标/徽标显示
5. 校验空列表页态、加载态与失败提示

## 七、常见问题
- 访问不了 Swagger：确认后端已启动（端口 8000、路径 `/api`），检查本机防火墙或跨网段访问
- 跨域问题（Web）：后端开启 CORS 或前端通过代理转发
- 小程序请求失败：开发阶段可临时忽略合法域名校验；真机需配置 HTTPS 合法域名
- 数据为空：确认 `userId` 正确（示例：`YT_USER_001`、`YT_USER_002` 等），并确保测试数据已插入

## 八、回归检查清单
- 分页正确（切换页码/大小行为一致）
- 列表数据与统计数量一致（相同 `userId`）
- 状态筛选准确（PENDING/REFUNDED）
- 时间展示格式符合产品诉求
- 错误处理与提示文案一致

---
如需我协助用指定 `userId` 做一次全链路联调验证，请告知用户 ID 与页码信息。 