package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 易通无人柜订单实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hibianli_orders")
public class YitongOrder {
    
    /**
     * 订单ID
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    
    /**
     * 订单编号
     */
    @TableField("order_code")
    private String orderCode;
    
    /**
     * 易通订单号
     */
    @TableField("hbl_order_no")
    private String yitongOrderNo;
    
    /**
     * 关联事件ID
     */
    @TableField("event_id")
    private Long eventId;
    
    /**
     * 设备编码
     */
    @TableField("device_code")
    private String deviceCode;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;
    
    /**
     * 订单金额
     */
    @TableField("order_amount")
    private BigDecimal orderAmount;
    
    /**
     * 商品金额
     */
    @TableField("product_amount")
    private BigDecimal productAmount;
    
    /**
     * 优惠金额
     */
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;
    
    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    
    /**
     * 订单状态
     */
    @TableField("order_status")
    private String orderStatus;
    
    /**
     * 支付状态
     */
    @TableField("pay_status")
    private String payStatus;
    
    /**
     * 支付类型 0-未知，5-支付宝扫码，6-支付宝刷脸，7-微信扫码，8-微信刷脸
     */
    @TableField("pay_type")
    private Integer payType;
    
    /**
     * 第三方支付订单号
     */
    @TableField("third_pay_order_id")
    private String thirdPayOrderId;
    
    /**
     * 支付渠道 alipay-支付宝 wxpay-微信
     */
    @TableField("channel_type")
    private String channelType;
    
    /**
     * 交易类型 PAY-支付 REFUND-退款
     */
    @TableField("trade_type")
    private String tradeType;
    
    /**
     * 分账金额(单位:分)
     */
    @TableField("share_amount")
    private BigDecimal shareAmount;
    
    /**
     * 支付时间
     */
    @TableField("trade_time")
    private String tradeTime;
    
    /**
     * 订单过期时间
     */
    @TableField("expired_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredAt;
    
    /**
     * 随机字符串
     */
    @TableField("nonce_str")
    private String nonceStr;
    
    /**
     * 请求序列号
     */
    @TableField("request_serial")
    private String requestSerial;
    
    /**
     * 时间戳
     */
    @TableField("time_stamp")
    private String timeStamp;
    
    /**
     * 签名
     */
    @TableField("sign")
    private String sign;
    
    /**
     * 公司ID
     */
    @TableField("company_id")
    private Integer companyId;
    
    /**
     * 订单备注
     */
    @TableField("remark")
    private String remark;
    
    /**
     * 预留字段1
     */
    @TableField("reserved_field1")
    private String reservedField1;
    
    /**
     * 预留字段2
     */
    @TableField("reserved_field2")
    private String reservedField2;
    
    /**
     * 预留字段3
     */
    @TableField("reserved_field3")
    private String reservedField3;
    
    /**
     * 预留字段4
     */
    @TableField("reserved_field4")
    private String reservedField4;
    
    /**
     * 预留字段5
     */
    @TableField("reserved_field5")
    private String reservedField5;
    
    /**
     * 是否删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    /**
     * 订单商品明细（非数据库字段）
     */
    @TableField(exist = false)
    private List<YitongOrderItem> orderItems;
    
    /**
     * 设备信息（非数据库字段）
     */
    @TableField(exist = false)
    private YitongDevice device;
    
    /**
     * 用户信息（非数据库字段）
     */
    @TableField(exist = false)
    private YitongUser user;
}
