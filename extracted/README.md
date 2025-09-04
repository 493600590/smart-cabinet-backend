# 无人柜系统后端服务

基于Spring Boot + MyBatis-Plus构建的无人柜系统后端服务，提供完整的用户管理、商品管理、订单管理、设备管理等功能。

## 技术架构

- **后端框架**: Spring Boot 2.7.8
- **ORM框架**: MyBatis-Plus 3.5.3
- **数据库**: MySQL 8.0+
- **缓存**: Redis
- **消息队列**: MQTT
- **安全认证**: Spring Security + JWT
- **API文档**: Swagger 3.0

## 项目结构

```
smart-cabinet-backend/
├── src/main/java/com/smartcabinet/
│   ├── config/                    # 配置类
│   │   ├── MyBatisPlusConfig.java
│   │   ├── RedisConfig.java
│   │   ├── SecurityConfig.java
│   │   ├── MqttConfig.java
│   │   └── WebConfig.java
│   ├── controller/                # 控制器层
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   ├── OrderController.java
│   │   └── DeviceController.java
│   ├── service/                   # 服务层
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── OrderService.java
│   │   ├── DeviceService.java
│   │   └── impl/                  # 服务实现
│   ├── mapper/                    # 数据访问层
│   │   ├── UserMapper.java
│   │   ├── ProductMapper.java
│   │   ├── OrderMapper.java
│   │   └── DeviceMapper.java
│   ├── entity/                    # 实体类
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── Device.java
│   │   └── ...
│   ├── common/                    # 公共模块
│   │   └── result/
│   │       ├── Result.java
│   │       └── ResultCode.java
│   ├── utils/                     # 工具类
│   │   ├── JwtUtil.java
│   │   └── RedisUtil.java
│   └── SmartCabinetApplication.java
├── src/main/resources/
│   ├── application.yml            # 应用配置
│   ├── mapper/                    # MyBatis XML文件
│   └── sql/                       # 数据库脚本
│       ├── schema.sql             # 建表脚本
│       └── data.sql               # 初始化数据
└── pom.xml                        # Maven配置
```

## 核心功能模块

### 1. 用户管理模块
- 用户注册与登录
- 微信小程序授权登录
- JWT令牌认证
- 用户信息管理
- 信用体系

### 2. 商品管理模块
- 商品信息维护
- 分类管理
- 库存监控
- 上下架管理
- 乐观锁库存控制

### 3. 订单管理模块
- 订单创建与支付
- 订单状态流转
- 退款处理
- 订单统计分析

### 4. 设备管理模块
- 设备注册与管理
- 实时状态监控
- MQTT通信
- 远程开关门控制
- 故障告警

## 数据库设计

系统包含以下核心数据表：
- `users` - 用户信息表
- `categories` - 商品分类表
- `products` - 商品信息表
- `devices` - 设备信息表
- `device_status` - 设备状态记录表
- `inventory` - 库存表
- `orders` - 订单信息表
- `order_items` - 订单商品表
- `operation_logs` - 操作日志表

## 快速开始

### 1. 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- MQTT Broker (如Mosquitto)

### 2. 数据库初始化
```sql
-- 执行建表脚本
source src/main/resources/sql/schema.sql

-- 导入初始化数据
source src/main/resources/sql/data.sql
```

### 3. 配置修改
修改 `application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_cabinet
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

### 4. 启动应用
```bash
mvn spring-boot:run
```

### 5. 访问接口
- 应用地址: http://localhost:8080/api
- API文档: http://localhost:8080/swagger-ui.html
- 数据库监控: http://localhost:8080/druid (admin/admin123)

## API接口示例

### 用户注册
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "testuser",
  "passwordHash": "123456",
  "phone": "13800138001",
  "email": "test@example.com"
}
```

### 用户登录
```bash
POST /api/users/login
Content-Type: application/json

{
  "phone": "13800138001",
  "password": "123456"
}
```

### 获取商品列表
```bash
GET /api/products?pageNum=1&pageSize=10&categoryId=1&status=ON_SHELF
```

### 创建订单
```bash
POST /api/orders
Content-Type: application/json

{
  "userId": 1,
  "deviceId": "SC001",
  "items": [
    {
      "productId": 1,
      "productName": "可口可乐 330ml",
      "price": 3.50,
      "quantity": 2
    }
  ]
}
```

## MQTT通信

### 设备上报主题
- `device/{deviceId}/status` - 设备状态上报
- `device/{deviceId}/sensor` - 传感器数据上报

### 设备控制主题
- `device/{deviceId}/command` - 设备控制指令

### 消息格式示例
```json
// 开门指令
{
  "cmd": "OPEN_DOOR",
  "params": {
    "doorId": "1",
    "timeout": 30
  },
  "requestId": "REQ123456"
}

// 状态上报
{
  "status": "ONLINE",
  "doorStatus": ["CLOSED", "OPENED", "CLOSED"],
  "sensors": {
    "temperature": 25.5,
    "humidity": 45,
    "weight": [1200, 800, 1500]
  },
  "timestamp": 1622505600000
}
```

## 部署说明

### Docker部署
```bash
# 构建镜像
docker build -t smart-cabinet-backend .

# 运行容器
docker run -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host:3306/smart_cabinet \
  -e SPRING_REDIS_HOST=redis-host \
  smart-cabinet-backend
```

### 生产环境配置
1. 修改JWT密钥
2. 配置HTTPS证书
3. 设置数据库连接池
4. 配置日志级别
5. 启用应用监控

## 开发规范

### 代码规范
- 使用统一的代码格式化规则
- 遵循RESTful API设计规范
- 添加适当的注释和文档
- 编写单元测试

### 数据库规范
- 使用乐观锁处理并发更新
- 合理使用索引优化查询
- 定期清理过期数据
- 备份重要数据

### 安全规范
- 所有API接口使用HTTPS
- 敏感数据加密存储
- 实施访问频率限制
- 记录操作审计日志

## 许可证

本项目采用 MIT 许可证。
