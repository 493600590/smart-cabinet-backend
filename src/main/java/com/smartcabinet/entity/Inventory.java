package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 库存实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("inventory")
public class Inventory {
    
    /**
     * 库存ID
     */
    @TableId(value = "inventory_id", type = IdType.AUTO)
    private Long inventoryId;
    
    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;
    
    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;
    
    /**
     * 货道编号
     */
    @TableField("slot_number")
    private String slotNumber;
    
    /**
     * 当前库存数量
     */
    @TableField("current_stock")
    private Integer currentStock;
    
    /**
     * 最大容量
     */
    @TableField("max_capacity")
    private Integer maxCapacity;
    
    /**
     * 预警阈值
     */
    @TableField("warning_threshold")
    private Integer warningThreshold;
    
    /**
     * 最后补货时间
     */
    @TableField("last_refill_time")
    private LocalDateTime lastRefillTime;
    
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
