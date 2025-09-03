package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 商品分类实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("categories")
public class Category {
    
    /**
     * 分类ID
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;
    
    /**
     * 分类名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 分类描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 父分类ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 排序字段
     */
    @TableField("sort_order")
    private Integer sortOrder;
    
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
