# 无人柜系统开发指南

## 系统概述

本项目是一个完整的无人柜系统后端服务，基于Spring Boot 2.7.8构建，包含用户管理、商品管理、订单管理、设备管理等核心功能模块。

## 已生成的框架结构

### 核心实体类 (Entity)
- `User.java` - 用户实体
- `Product.java` - 商品实体  
- `Category.java` - 商品分类实体
- `Order.java` - 订单实体
- `OrderItem.java` - 订单商品实体
- `Device.java` - 设备实体
- `DeviceStatus.java` - 设备状态记录实体
- `Inventory.java` - 库存实体
- `OperationLog.java` - 操作日志实体

### 数据访问层 (Mapper)
- `UserMapper.java` - 用户数据访问接口
- `ProductMapper.java` - 商品数据访问接口
- `CategoryMapper.java` - 分类数据访问接口
- `OrderMapper.java` - 订单数据访问接口
- `OrderItemMapper.java` - 订单商品数据访问接口
- `DeviceMapper.java` - 设备数据访问接口
- `DeviceStatusMapper.java` - 设备状态数据访问接口
- `InventoryMapper.java` - 库存数据访问接口
- `OperationLogMapper.java` - 操作日志数据访问接口

### 业务服务层 (Service)
- `UserService.java` & `UserServiceImpl.java` - 用户服务
- `ProductService.java` & `ProductServiceImpl.java` - 商品服务
- `OrderService.java` & `OrderServiceImpl.java` - 订单服务
- `DeviceService.java` & `DeviceServiceImpl.java` - 设备服务
- `OrderItemService.java` & `OrderItemServiceImpl.java` - 订单商品服务

### 控制器层 (Controller)
- `UserController.java` - 用户接口控制器
- `ProductController.java` - 商品接口控制器
- `OrderController.java` - 订单接口控制器
- `DeviceController.java` - 设备接口控制器

### 配置类 (Config)
- `SecurityConfig.java` - Spring Security安全配置
- `RedisConfig.java` - Redis缓存配置
- `MqttConfig.java` - MQTT通信配置
- `MyBatisPlusConfig.java` - MyBatis-Plus配置
- `WebConfig.java` - Web基础配置

### 工具类 (Utils)
- `JwtUtil.java` - JWT令牌工具类
- `RedisUtil.java` - Redis操作工具类

### 数据库脚本
- `src/main/resources/sql/schema.sql` - 数据库建表脚本
- `src/main/resources/sql/data.sql` - 初始化数据脚本

## 技术栈配置

### 已配置的依赖
- **Spring Boot 2.7.8** - 基础框架
- **MyBatis-Plus 3.5.3** - ORM框架
- **MySQL Connector 8.0.33** - 数据库连接器
- **Druid 1.2.16** - 数据库连接池
- **Redis** - 缓存中间件
- **Spring Security** - 安全框架
- **JWT 0.11.5** - 令牌认证
- **MQTT** - 设备通信协议
- **Swagger 3.0** - API文档
- **Fastjson 2.0.25** - JSON处理
- **Hutool 5.8.15** - 工具库

### 配置文件说明
- `application.yml` - 主配置文件，包含数据库、Redis、MQTT等配置
- `pom.xml` - Maven依赖管理配置

## 快速开始

### 1. 环境准备
- JDK 8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- MQTT Broker (如Mosquitto)

### 2. 数据库初始化
```sql
-- 1. 创建数据库
CREATE DATABASE smart_cabinet DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 执行建表脚本
source src/main/resources/sql/schema.sql

-- 3. 导入初始化数据
source src/main/resources/sql/data.sql
```

### 3. 配置修改
修改 `src/main/resources/application.yml` 中的配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/smart_cabinet
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
    password: your_redis_password

mqtt:
  broker:
    url: tcp://localhost:1883
    username: your_mqtt_username
    password: your_mqtt_password
```

### 4. 启动应用
```bash
mvn spring-boot:run
```

### 5. 验证部署
- 应用地址: http://localhost:8080/api
- 健康检查: http://localhost:8080/api/actuator/health
- API文档: http://localhost:8080/swagger-ui.html
- 数据库监控: http://localhost:8080/druid

## API接口说明

### 用户接口
- `POST /api/users/register` - 用户注册
- `POST /api/users/login` - 用户登录
- `POST /api/users/wechat-login` - 微信登录
- `GET /api/users/profile` - 获取用户信息
- `PUT /api/users/profile` - 更新用户信息

### 商品接口
- `GET /api/products` - 分页查询商品
- `GET /api/products/{id}` - 获取商品详情
- `GET /api/products/hot` - 获取热门商品
- `POST /api/products` - 创建商品（管理员）
- `PUT /api/products/{id}` - 更新商品（管理员）

### 订单接口
- `POST /api/orders` - 创建订单
- `GET /api/orders` - 查询用户订单
- `GET /api/orders/{orderNo}` - 获取订单详情
- `PUT /api/orders/{orderNo}/pay` - 支付订单
- `PUT /api/orders/{orderNo}/cancel` - 取消订单

### 设备接口
- `GET /api/devices` - 分页查询设备
- `GET /api/devices/{deviceId}` - 获取设备详情
- `GET /api/devices/{deviceId}/status` - 获取设备状态
- `POST /api/devices/{deviceId}/open-door` - 开门指令
- `PUT /api/devices/{deviceId}/heartbeat` - 更新心跳

## 业务流程

### 1. 用户购物流程
1. 用户扫码开门
2. 系统验证用户身份
3. 发送开门指令
4. 用户取货关门
5. 系统检测商品变化
6. 生成订单并发起支付
7. 支付完成，订单完成

### 2. 设备管理流程
1. 设备注册到系统
2. 定时发送心跳包
3. 上报状态和传感器数据
4. 系统监控设备健康状态
5. 异常时发送告警

### 3. 库存管理流程
1. 商品入库，更新库存数量
2. 用户购买，减少库存
3. 低库存自动预警
4. 生成补货计划

## 注意事项

### 开发规范
1. 所有Controller方法必须返回统一的Result格式
2. 使用@Valid注解进行参数校验
3. 业务异常统一通过RuntimeException抛出
4. 数据库操作必须考虑事务一致性
5. 敏感数据必须加密存储

### 安全要求
1. 所有API接口使用JWT认证
2. 密码使用BCrypt加密存储
3. SQL注入防护通过参数化查询
4. 接口访问频率限制
5. 操作日志完整记录

### 性能优化
1. 使用Redis缓存热点数据
2. 数据库查询添加合适索引
3. 分页查询避免深度分页
4. 库存更新使用乐观锁
5. 批量操作提升效率

## 后续开发计划

### 待实现功能
1. 微信小程序登录集成
2. 支付接口对接（微信支付、支付宝）
3. MQTT设备通信完善
4. 数据分析和报表功能
5. 图片上传和文件管理
6. 消息推送服务
7. 定时任务调度
8. 系统监控和告警

### 扩展方向
1. 多租户支持
2. 分布式部署
3. 微服务架构改造
4. 容器化部署
5. 自动化测试
6. CI/CD流水线

## 联系信息

如有问题，请联系开发团队或查看项目文档。
