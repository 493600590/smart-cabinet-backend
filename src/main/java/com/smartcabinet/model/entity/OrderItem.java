package com.smartcabinet.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单商品实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_items")
public class OrderItem {
    
    /**
     * 订单商品ID
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;
    
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;
    
    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;
    
    /**
     * 商品名称(快照)
     */
    @TableField("product_name")
    private String productName;
    
    /**
     * 商品价格(快照)
     */
    @TableField("product_price")
    private BigDecimal productPrice;
    
    /**
     * 购买数量
     */
    @TableField("quantity")
    private Integer quantity;
    
    /**
     * 小计金额
     */
    @TableField("subtotal")
    private BigDecimal subtotal;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
