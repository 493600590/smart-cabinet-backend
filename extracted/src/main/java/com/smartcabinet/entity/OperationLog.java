package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("operation_logs")
public class OperationLog {
    
    /**
     * 日志ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 操作类型 LOGIN-登录 PURCHASE-购买 REFUND-退款 DEVICE_CONTROL-设备控制
     */
    @TableField("operation_type")
    private String operationType;
    
    /**
     * 操作描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;
    
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;
    
    /**
     * 操作结果 SUCCESS-成功 FAILURE-失败
     */
    @TableField("result")
    private String result;
    
    /**
     * 客户端IP
     */
    @TableField("client_ip")
    private String clientIp;
    
    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;
    
    /**
     * 额外数据(JSON格式)
     */
    @TableField("extra_data")
    private String extraData;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
