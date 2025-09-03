package com.smartcabinet.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("products")
public class Product {
    
    /**
     * 商品ID
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Long productId;
    
    /**
     * 商品名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;
    
    /**
     * 商品价格
     */
    @TableField("price")
    private BigDecimal price;
    
    /**
     * 库存数量
     */
    @TableField("stock")
    private Integer stock;
    
    /**
     * 商品图片URL
     */
    @TableField("image_url")
    private String imageUrl;
    
    /**
     * 商品描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 商品状态 ON_SHELF-上架 OFF_SHELF-下架
     */
    @TableField("status")
    private String status;
    
    /**
     * 版本号(乐观锁)
     */
    @TableField("version")
    @Version
    private Integer version;
    
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
