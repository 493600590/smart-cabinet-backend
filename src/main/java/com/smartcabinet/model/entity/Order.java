package com.smartcabinet.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("orders")
public class Order {
    
    /**
     * 订单ID
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;
    
    /**
     * 订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    
    /**
     * 订单状态 PENDING_PAYMENT-待支付 PAID-已支付 COMPLETED-已完成 REFUNDED-已退款 CANCELLED-已取消
     */
    @TableField("status")
    private String status;
    
    /**
     * 支付方式 WECHAT-微信支付 ALIPAY-支付宝
     */
    @TableField("payment_method")
    private String paymentMethod;
    
    /**
     * 支付时间
     */
    @TableField("payment_time")
    private LocalDateTime paymentTime;
    
    /**
     * 支付交易号
     */
    @TableField("payment_transaction_id")
    private String paymentTransactionId;
    
    /**
     * 备注信息
     */
    @TableField("remark")
    private String remark;
    
    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
