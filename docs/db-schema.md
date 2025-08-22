### 微信小程序无人柜后端管理系统 — 数据库表设计（Markdown文档）

本文档面向基于微信小程序的无人柜（智能货柜）后端管理系统的数据建模与约束设计。目标是覆盖核心业务（扫码开柜、取走商品、自动下单、微信支付、库存补货、设备运维、结算对账、活动会员与通知）所需的数据表、字段、主外键与约束关系。

---

## 设计范围与总体原则
- **命名规范**：
  - 表名与字段名使用小写下划线风格；示例：`order_item`、`cabinet_device`。
  - 主键统一为 `id`（建议 `bigint` 或雪花ID字符串）；时间统一 `created_at`、`updated_at`。
- **约束原则**：
  - 外键均显式建立；状态字段使用受限枚举（或字典表/检查约束）。
  - 关键业务表严格控制删除策略：订单、支付、库存台账、审计日志一律不允许物理删除。
- **多租户**：支持多商户（`merchant`），门店（`store`）归属商户，设备（`cabinet_device`）归属门店。
- **性能与扩展**：为高频查询字段建立索引；流水类表（交易、库存台账、日志）支持分区或冷热分离。

---

## 核心流程简述
1. 用户微信扫码设备二维码 → 后端创建一次开柜会话（`device_session`）并开锁。
2. 用户取/放商品 → 关门后设备上报差异 → 生成订单（`order`、`order_item`），占减库存（`inventory_movement`）。
3. 调起微信支付（`payment`），支付成功后订单完成；失败则关闭/取消。
4. 库存管理：按货道（`cabinet_cell`）与批次（`stock_batch`）管理库存（`inventory`），补货用（`replenishment_order`）。
5. 设备心跳、告警、固件等通过运维表（`device_event_log`、`device_alarm`、`device_firmware`）管理。
6. 运营：优惠券/活动（`coupon_*`、`promotion`）、会员积分（`points_*`）。
7. 结算：对账单（`settlement_statement`、`settlement_item`）对商户或门店结算。

---

## 模块与表清单
- **多租户与组织**：`merchant`、`store`
- **用户与权限**：`app_user`（小程序用户）、`staff_user`、`role`、`permission`、`staff_user_role`、`role_permission`
- **设备与货道**：`cabinet_device`、`cabinet_cell`、`device_session`、`device_event_log`、`device_alarm`、`firmware`、`device_firmware`
- **商品与定价**：`category`、`brand`、`product_spu`、`product_sku`、`sku_barcode`、`sku_price`
- **库存与补货**：`stock_batch`、`inventory`、`inventory_movement`、`replenishment_order`、`replenishment_item`
- **交易与支付**：`order`、`order_item`、`payment`、`refund`、`invoice`（可选）
- **活动与会员**：`promotion`、`coupon`、`coupon_grant`、`coupon_use`、`member_level`、`points_account`、`points_txn`
- **结算与对账**：`settlement_statement`、`settlement_item`
- **消息与审计**：`wx_message_log`、`audit_log`
- **通用字典（可选）**：`dict`、`dict_item`

---

## 数据表详细设计（字段/主外键/索引/说明）

> 字段类型为建议值，可按选用数据库调整（如 `bigint`/`varchar`/`decimal(p,s)`/`jsonb`/`enum`）。
> 表中未特别标注的时间字段均建议 `not null` 且默认 `now()`。

### 多租户与组织

#### `merchant`（商户）
- `id` PK
- `name` 唯一，商户名称
- `contact_name`、`contact_phone`
- `status` 枚举：active, suspended
- `config` json（如分账/费率）
- `created_at`、`updated_at`
- 约束与索引：
  - 唯一键：`(name)`
  - 业务外键被多表引用，禁止删除；`status` 控制启停

#### `store`（门店/点位）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `name`
- `code` 唯一
- `province`、`city`、`district`、`address`
- `status`：open, closed
- `created_at`、`updated_at`
- 索引：
  - `idx_store_merchant`(`merchant_id`)
  - 唯一键：`(code)`

### 用户与权限

#### `app_user`（小程序用户/消费者）
- `id` PK
- `merchant_id` FK→`merchant.id`（单小程序多商户可复用；如单租户可省略）
- `openid` 唯一
- `unionid` 可唯一
- `nickname`、`avatar_url`、`gender`
- `mobile` 唯一（可空）
- `status`：active, banned
- `created_at`、`updated_at`
- 索引：
  - 唯一键：`(openid)`、可选 `(unionid)`、可选 `(mobile)`

#### `staff_user`（后台/运维/补货人员）
- `id` PK
- `merchant_id` FK→`merchant.id`（平台级账号可空）
- `username` 唯一
- `password_hash`
- `real_name`、`mobile`
- `status`：active, disabled
- `created_at`、`updated_at`

#### `role`
- `id` PK
- `merchant_id` FK→`merchant.id`（商户自定义角色）
- `code`、`name`（`code` 在同商户唯一）
- `created_at`、`updated_at`
- 唯一键：`(merchant_id, code)`

#### `permission`
- `id` PK
- `code` 唯一
- `name`、`desc`
- `created_at`、`updated_at`

#### `staff_user_role`
- `id` PK
- `staff_user_id` FK→`staff_user.id`
- `role_id` FK→`role.id`
- 唯一键：`(staff_user_id, role_id)`

#### `role_permission`
- `id` PK
- `role_id` FK→`role.id`
- `permission_id` FK→`permission.id`
- 唯一键：`(role_id, permission_id)`

### 设备与货道

#### `cabinet_device`（设备/货柜）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `store_id` FK→`store.id`
- `device_sn` 唯一（序列号）
- `qr_code` 唯一（供扫码）
- `model`、`firmware_version`
- `status`：online, offline, maintenance, disabled
- `last_heartbeat_at`
- `location_desc`（点位描述）
- `created_at`、`updated_at`
- 索引：`idx_device_store`(`store_id`)

#### `cabinet_cell`（货道/隔间）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `cell_no`（设备内唯一编号）唯一键：`(device_id, cell_no)`
- `sensor_type`：weight, vision, rfid, none
- `capacity`（最大容纳数量/重量）
- `current_sku_id` FK→`product_sku.id`（可空）
- `status`：enabled, disabled, faulty
- `created_at`、`updated_at`

#### `device_session`（开柜会话）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `app_user_id` FK→`app_user.id`（游客可空）
- `open_method`：qrcode, remote, staff
- `opened_at`、`closed_at`
- `result`：success, canceled, timeout, error
- `order_id` FK→`order.id`（关门后生成的订单，可空）
- `meta` json（传感器快照、图像摘要）
- 索引：`idx_session_device_time`(`device_id`, `opened_at`)

#### `device_event_log`（设备事件/心跳/状态）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `event_type`：heartbeat, door_open, door_close, weight_change, network, upgrade
- `event_at`
- `payload` json
- 索引：`idx_event_device_time`(`device_id`, `event_at`)

#### `device_alarm`（设备告警）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `level`：info, warn, critical
- `code`：door_fault, sensor_fault, temp_high, offline, power_low, unknown
- `status`：open, acknowledged, closed
- `raised_at`、`closed_at`
- `desc`
- 索引：`idx_alarm_device_status`(`device_id`, `status`)

#### `firmware`
- `id` PK
- `model`
- `version` 唯一键：`(model, version)`
- `file_url`、`checksum`
- `release_notes`
- `created_at`

#### `device_firmware`
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `firmware_id` FK→`firmware.id`
- `apply_status`：pending, in_progress, success, failed
- `scheduled_at`、`applied_at`
- 唯一键：`(device_id, firmware_id)`

### 商品与定价

#### `category`
- `id` PK
- `parent_id` FK→`category.id`（可空）
- `name`
- `path`（如 1/3/9）
- `sort`
- 唯一键：`(parent_id, name)`

#### `brand`
- `id` PK
- `name` 唯一
- `logo_url`

#### `product_spu`
- `id` PK
- `merchant_id` FK→`merchant.id`
- `name`、`category_id` FK→`category.id`、`brand_id` FK→`brand.id`
- `status`：on, off
- `attrs` json
- 唯一键：`(merchant_id, name)`

#### `product_sku`
- `id` PK
- `spu_id` FK→`product_spu.id`
- `sku_name`
- `specs` json（重量/容量/口味等）
- `unit`（件/瓶/克）
- `status`：on, off
- 唯一键：`(spu_id, sku_name)`

#### `sku_barcode`
- `id` PK
- `sku_id` FK→`product_sku.id`
- `barcode` 唯一
- 唯一键：`(sku_id, barcode)`

#### `sku_price`（售卖价，支持按门店/设备/时间）
- `id` PK
- `sku_id` FK→`product_sku.id`
- `scope_type`：global, store, device
- `store_id` FK→`store.id`（当 scope=store）
- `device_id` FK→`cabinet_device.id`（当 scope=device）
- `price` decimal(10,2)
- `start_at`、`end_at`（可空表示长期）
- 组合唯一：`(sku_id, scope_type, coalesce(store_id,0), coalesce(device_id,0), start_at)`
- 索引：`idx_price_lookup`(`sku_id`, `scope_type`, `store_id`, `device_id`)

### 库存与补货

#### `stock_batch`（批次/保质期）
- `id` PK
- `sku_id` FK→`product_sku.id`
- `lot_no`、`mfg_date`、`exp_date`
- 唯一键：`(sku_id, lot_no)`

#### `inventory`（库存现量，按设备/货道/批次）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `cell_id` FK→`cabinet_cell.id`（可空：设备级）
- `sku_id` FK→`product_sku.id`
- `stock_batch_id` FK→`stock_batch.id`（可空）
- `quantity` int（件数）或 `weight` decimal（如称重柜，可并存两个字段）
- 唯一键：`(device_id, coalesce(cell_id,0), sku_id, coalesce(stock_batch_id,0))`
- 索引：`idx_inventory_device_sku`(`device_id`, `sku_id`)

#### `inventory_movement`（库存台账/流水）
- `id` PK
- `device_id`、`cell_id`、`sku_id`、`stock_batch_id`
- `movement_type`：inbound, outbound, adjust, reserve, release
- `ref_type`：order, replenishment, audit, init, refund
- `ref_id`（关联单据ID，如 `order.id`/`replenishment_order.id`）
- `delta_qty`（正负）
- `balance_after`（可选）
- `occurred_at`
- 索引：`idx_move_device_time`(`device_id`, `occurred_at`)，`idx_move_ref`(`ref_type`, `ref_id`)

#### `replenishment_order`（补货单）
- `id` PK
- `device_id` FK→`cabinet_device.id`
- `created_by` FK→`staff_user.id`
- `status`：draft, submitted, completed, canceled
- `planned_at`、`completed_at`
- `remark`
- 索引：`idx_repl_device_status`(`device_id`, `status`)

#### `replenishment_item`
- `id` PK
- `replenishment_id` FK→`replenishment_order.id`
- `sku_id` FK→`product_sku.id`
- `stock_batch_id` FK→`stock_batch.id`（可空）
- `cell_id` FK→`cabinet_cell.id`（目标货道）
- `planned_qty`、`actual_qty`
- 唯一键：`(replenishment_id, sku_id, coalesce(stock_batch_id,0), coalesce(cell_id,0))`

### 交易与支付

#### `order`（订单）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `store_id` FK→`store.id`
- `device_id` FK→`cabinet_device.id`
- `app_user_id` FK→`app_user.id`
- `device_session_id` FK→`device_session.id`
- `order_no` 唯一（业务单号）
- `status`：created, waiting_payment, paid, canceled, closed, refunded, refunding
- `original_amount` decimal(10,2)（原价合计）
- `discount_amount` decimal(10,2)（优惠合计）
- `payable_amount` decimal(10,2)（应付）
- `paid_amount` decimal(10,2)（实付）
- `coupon_id` FK→`coupon.id`（可空）
- `payment_time`、`close_time`
- `remark`
- `extra` json（设备识别快照、差异明细）
- 唯一键：`(order_no)`
- 索引：`idx_order_user_time`(`app_user_id`, `created_at`)、`idx_order_device_time`(`device_id`, `created_at`)

#### `order_item`
- `id` PK
- `order_id` FK→`order.id` on delete restrict
- `sku_id` FK→`product_sku.id`
- `sku_name_snapshot`（下单时快照）
- `unit_price`、`sale_price`（单价与成交价）
- `quantity`、`weight`（二选一或并存）
- `discount_amount`
- `cell_id` FK→`cabinet_cell.id`（来源货道，可空）
- 索引：`idx_order_item_order`(`order_id`)

#### `payment`（支付流水）
- `id` PK
- `order_id` FK→`order.id`
- `channel`：wechat
- `trade_type`：JSAPI
- `out_trade_no` 唯一（商户订单号）
- `transaction_id`（微信支付单号）唯一可空（未成功前）
- `amount_total`、`amount_payer`
- `currency`：CNY
- `status`：initiated, success, failed, closed
- `payer_openid`
- `notify_payload` json（支付回调原文）
- `paid_at`
- 唯一键：`(out_trade_no)`；索引：`idx_pay_order`(`order_id`)

#### `refund`（退款单）
- `id` PK
- `order_id` FK→`order.id`
- `payment_id` FK→`payment.id`
- `out_refund_no` 唯一
- `refund_id`（微信退款单号）唯一可空
- `amount_refund`、`reason`
- `status`：initiated, success, failed, closed
- `notified_payload` json
- `refunded_at`
- 唯一键：`(out_refund_no)`；索引：`idx_refund_order`(`order_id`)

#### `invoice`（发票，可选）
- `id` PK
- `order_id` FK→`order.id`
- `type`：personal, company
- `title`、`tax_no`、`email`
- `status`：requested, issued, canceled
- `issued_at`

### 活动与会员

#### `promotion`（促销规则）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `name`、`type`：full_reduction, discount, time_price
- `scope`：sku, category, store, device
- `rule` json（门槛/折扣/适用范围）
- `start_at`、`end_at`、`status`：on, off

#### `coupon`（券模板）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `name`、`type`：cash, discount
- `rule` json（面额/门槛/适用范围）
- `total`、`limit_per_user`
- `valid_from`、`valid_to`、`status`
- 唯一键：`(merchant_id, name)`

#### `coupon_grant`（券发放）
- `id` PK
- `coupon_id` FK→`coupon.id`
- `app_user_id` FK→`app_user.id`
- `code` 唯一（可选）
- `granted_at`、`expire_at`
- `status`：unused, used, expired, void
- 唯一键：`(coupon_id, app_user_id, code)`（code 可空则唯一退化）

#### `coupon_use`（券核销）
- `id` PK
- `coupon_grant_id` FK→`coupon_grant.id`
- `order_id` FK→`order.id`
- `used_at`
- 唯一键：`(coupon_grant_id)`

#### `member_level`
- `id` PK
- `merchant_id` FK→`merchant.id`
- `code`、`name`、`rule` json（升级条件/权益）
- 唯一键：`(merchant_id, code)`

#### `points_account`
- `id` PK
- `merchant_id` FK→`merchant.id`
- `app_user_id` FK→`app_user.id`
- `points_balance`
- 唯一键：`(merchant_id, app_user_id)`

#### `points_txn`（积分流水）
- `id` PK
- `account_id` FK→`points_account.id`
- `ref_type`：order, activity, adjust
- `ref_id`
- `delta_points`、`balance_after`
- `occurred_at`

### 结算与对账

#### `settlement_statement`（结算单）
- `id` PK
- `merchant_id` FK→`merchant.id`
- `store_id`（可空）FK→`store.id`
- `period_start`、`period_end`
- `status`：pending, confirmed, paid
- `total_order_amount`、`total_refund_amount`、`fee_amount`、`payable_amount`
- `generated_at`、`paid_at`
- 唯一键：`(merchant_id, coalesce(store_id,0), period_start, period_end)`

#### `settlement_item`
- `id` PK
- `statement_id` FK→`settlement_statement.id`
- `order_id` FK→`order.id`
- `amount`（正负）
- 唯一键：`(statement_id, order_id)`

### 消息与审计

#### `wx_message_log`（微信消息/订阅消息）
- `id` PK
- `app_user_id` FK→`app_user.id`
- `template_id`
- `scene`：order_pay_success, refund_success, device_alarm, coupon_grant
- `payload` json
- `status`：sent, failed
- `sent_at`

#### `audit_log`（审计日志）
- `id` PK
- `operator_type`：staff, system, device
- `operator_id`（如 `staff_user.id`）
- `action`、`target_type`、`target_id`
- `details` json
- `created_at`
- 索引：`idx_audit_target`(`target_type`, `target_id`)

### 通用字典（可选）

#### `dict`
- `id` PK
- `code` 唯一
- `name`、`desc`

#### `dict_item`
- `id` PK
- `dict_id` FK→`dict.id`
- `item_code`、`item_name`、`sort`
- 唯一键：`(dict_id, item_code)`

---

## 关键外键与删除/更新策略（建议）
- `merchant`、`store`、`cabinet_device`：被大量引用，禁止物理删除；若需下线，置 `status`。
- `order`、`payment`、`refund`、`inventory_movement`、`audit_log`：禁止物理删除。
- `product_*`、`sku_price`：下架通过 `status` 或 `end_at`，避免硬删。
- 外键 `on delete` 策略：
  - 从属弱实体（如 `cabinet_cell` 依赖 `cabinet_device`）可 `on delete cascade`（谨慎，生产多用软删+状态）。
  - 交易/台账表一律 `restrict`。
- `on update` 一律 `restrict`，ID 不允许更新；业务编号如 `order_no` 唯一不可变。

---

## 典型索引建议
- 高频查询维度：时间、设备、门店、用户、订单号、支付单号。
- 示例：
  - `order(order_no)` 唯一；`(device_id, created_at)`、`(app_user_id, created_at)`
  - `payment(out_trade_no)`、`payment(transaction_id)`
  - `inventory(device_id, sku_id)`、`inventory_movement(device_id, occurred_at)`
  - `device_event_log(device_id, event_at)`、`device_alarm(device_id, status)`
  - `coupon_grant(app_user_id, status, expire_at)`

---

## 状态与枚举（建议取值）
- 订单 `order.status`：created → waiting_payment → paid → closed/canceled；退款流转：paid → refunding → refunded/failed
- 支付 `payment.status`：initiated → success/failed/closed
- 退款 `refund.status`：initiated → success/failed/closed
- 设备 `cabinet_device.status`：online/offline/maintenance/disabled
- 货道 `cabinet_cell.status`：enabled/disabled/faulty
- 会话 `device_session.result`：success/canceled/timeout/error
- 商品 `product_spu|sku.status`：on/off
- 券 `coupon_grant.status`：unused/used/expired/void
- 结算 `settlement_statement.status`：pending/confirmed/paid

---

## 关系概览（ER 要点）
- 商户 `merchant` 1—N 门店 `store`；门店 1—N 设备 `cabinet_device`；设备 1—N 货道 `cabinet_cell`。
- 用户 `app_user` 1—N 会话 `device_session`；会话 0/1—1 订单 `order`；订单 1—N 明细 `order_item`。
- 订单 1—N 支付 `payment`（一般1条）；订单 0—N 退款 `refund`。
- SKU 与价格 `sku_price` 可按全局/门店/设备覆盖，时间区间生效。
- 库存现量 `inventory` 与台账 `inventory_movement` 形成二套账；补货单 `replenishment_order` 驱动入库台账。
- 券模板 `coupon` → 发放 `coupon_grant` → 核销 `coupon_use` 关联到 `order`。
- 结算单 `settlement_statement` 汇总多笔 `order` 为 `settlement_item`。
- 权限：`staff_user` — M:N — `role` — M:N — `permission`。

---

## 关键业务约束与一致性规则
- 订单金额：`original_amount - discount_amount = payble_amount`，支付成功后 `paid_amount = payable_amount`。
- 库存扣减：订单从 `waiting_payment` 到 `paid` 前可使用“预占（reserve）→ 成交（outbound）/释放（release）”两段式；无人柜常在关门判定后直接 `outbound`，失败需创建反向台账或退款。
- 批次规则：如开启批次管理，`inventory` 与 `inventory_movement` 必须带 `stock_batch_id`；否则允许为空。
- 价格决策：查价顺序 device > store > global，选择有效期内最新一条。
- 券与促销叠加：由计算引擎在 `order` 生成时固化到金额字段；实际落库仅保存结果与规则快照。
- 退款：金额不得超 `paid_amount - 已退款额`；状态机与支付渠道回调保持幂等。

---

## 数据安全与审计
- 敏感信息（手机号、unionid）需加密或脱敏展示。
- 支付回调 `notify_payload` 原文只追加不修改；审计日志 `audit_log` 记录后台关键操作。

---

## 可选扩展
- 温控监测（冷柜）：`device_telemetry`（温湿度等时序数据）
- 图像识别源数据：对象存储 + `device_event_log` 引用
- 盘点/稽核：`stock_take`、`stock_take_item`

---

## 附：最小可用集（MVP）优先级建议
- P0（必须）：`merchant`、`store`、`cabinet_device`、`cabinet_cell`、`app_user`、`product_spu`、`product_sku`、`sku_barcode`、`sku_price`、`inventory`、`inventory_movement`、`device_session`、`order`、`order_item`、`payment`、`refund`、`device_event_log`
- P1（常用）：`replenishment_order`、`replenishment_item`、`device_alarm`、`settlement_statement`、`settlement_item`、`coupon_*`
- P2（增值）：`promotion`、`member_level`、`points_*`、`invoice`、`firmware`、`device_firmware`、`wx_message_log`、`audit_log`

---

以上为无人柜全链路数据库表设计与关键约束，适合直接落地实现。若需针对 MySQL 或 PostgreSQL 输出可执行 DDL（含字段类型、索引、检查约束），可在此文档基础上进一步细化。


