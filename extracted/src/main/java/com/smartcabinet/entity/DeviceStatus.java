package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 设备状态记录实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("device_status")
public class DeviceStatus {
    
    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;
    
    /**
     * 温度
     */
    @TableField("temperature")
    private BigDecimal temperature;
    
    /**
     * 湿度
     */
    @TableField("humidity")
    private BigDecimal humidity;
    
    /**
     * 柜门状态(JSON格式)
     */
    @TableField("door_status")
    private String doorStatus;
    
    /**
     * 传感器数据(JSON格式)
     */
    @TableField("sensor_data")
    private String sensorData;
    
    /**
     * 电池电量
     */
    @TableField("battery_level")
    private Integer batteryLevel;
    
    /**
     * 网络信号强度
     */
    @TableField("signal_strength")
    private Integer signalStrength;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
