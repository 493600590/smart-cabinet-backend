package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 易通无人柜商品实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hibianli_goods")
public class YitongGoods {
    
    /**
     * 云库商品ID
     */
    @TableId(value = "goods_id", type = IdType.INPUT)
    private Long goodsId;
    
    /**
     * 易通SKU ID
     */
    @TableField("hbl_sku_id")
    private String yitongSkuId;
    
    /**
     * 一维条码
     */
    @TableField("barcode")
    private String barcode;
    
    /**
     * 第三方映射CODE
     */
    @TableField("ext_sku_code")
    private String extSkuCode;
    
    /**
     * 商品名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 规格
     */
    @TableField("sku")
    private String sku;
    
    /**
     * 商品描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 售价
     */
    @TableField("price")
    private BigDecimal price;
    
    /**
     * 原价
     */
    @TableField("raw_price")
    private BigDecimal rawPrice;
    
    /**
     * 成本价
     */
    @TableField("cost_price")
    private BigDecimal costPrice;
    
    /**
     * 商品图片URL
     */
    @TableField("image_url")
    private String imageUrl;
    
    /**
     * 商品状态 1-上架 2-下架
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 商品类型 1-标品 2-非标品
     */
    @TableField("goods_type")
    private Integer goodsType;
    
    /**
     * 商品分类ID
     */
    @TableField("category_id")
    private Long categoryId;
    
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    
    /**
     * 重量(g)
     */
    @TableField("weight")
    private BigDecimal weight;
    
    /**
     * 体积(ml)
     */
    @TableField("volume")
    private BigDecimal volume;
    
    /**
     * 保质期(天)
     */
    @TableField("shelf_life")
    private Integer shelfLife;
    
    /**
     * 储存条件
     */
    @TableField("storage_condition")
    private String storageCondition;
    
    /**
     * 公司ID
     */
    @TableField("company_id")
    private Integer companyId;
    
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
}
