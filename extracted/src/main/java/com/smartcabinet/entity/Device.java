package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 设备实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("devices")
public class Device {
    
    /**
     * 设备ID
     */
    @TableId(value = "device_id", type = IdType.INPUT)
    private String deviceId;
    
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;
    
    /**
     * 设备型号
     */
    @TableField("model")
    private String model;
    
    /**
     * 安装位置
     */
    @TableField("location")
    private String location;
    
    /**
     * 详细地址
     */
    @TableField("address")
    private String address;
    
    /**
     * 设备状态 ONLINE-在线 OFFLINE-离线 FAULT-故障 MAINTENANCE-维护
     */
    @TableField("status")
    private String status;
    
    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;
    
    /**
     * 固件版本
     */
    @TableField("firmware_version")
    private String firmwareVersion;
    
    /**
     * 最后心跳时间
     */
    @TableField("last_heartbeat")
    private LocalDateTime lastHeartbeat;
    
    /**
     * 温度阈值上限
     */
    @TableField("temperature_max")
    private BigDecimal temperatureMax;
    
    /**
     * 温度阈值下限
     */
    @TableField("temperature_min")
    private BigDecimal temperatureMin;
    
    /**
     * 湿度阈值上限
     */
    @TableField("humidity_max")
    private BigDecimal humidityMax;
    
    /**
     * 湿度阈值下限
     */
    @TableField("humidity_min")
    private BigDecimal humidityMin;
    
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
