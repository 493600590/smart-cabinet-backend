package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 易通无人柜订单商品明细实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hibianli_order_items")
public class YitongOrderItem {
    
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
     * 订单编号
     */
    @TableField("order_code")
    private String orderCode;
    
    /**
     * 云库商品ID
     */
    @TableField("goods_id")
    private Long goodsId;
    
    /**
     * 易通SKU ID
     */
    @TableField("hbl_sku_id")
    private String yitongSkuId;
    
    /**
     * 商品条码
     */
    @TableField("barcode")
    private String barcode;
    
    /**
     * 第三方映射CODE
     */
    @TableField("ext_sku_code")
    private String extSkuCode;
    
    /**
     * 商品名称(快照)
     */
    @TableField("goods_name")
    private String goodsName;
    
    /**
     * 商品规格(快照)
     */
    @TableField("goods_sku")
    private String goodsSku;
    
    /**
     * 商品图片(快照)
     */
    @TableField("goods_image")
    private String goodsImage;
    
    /**
     * 商品单价(快照)
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;
    
    /**
     * 商品原价(快照)
     */
    @TableField("raw_price")
    private BigDecimal rawPrice;
    
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
     * 优惠金额
     */
    @TableField("discount_amount")
    private BigDecimal discountAmount;
    
    /**
     * 实付金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    
    /**
     * 商品状态 1-正常 2-非友好 10-未上架
     */
    @TableField("goods_status")
    private Integer goodsStatus;
    
    /**
     * 识别状态 1-正常识别 2-异常识别
     */
    @TableField("recognition_status")
    private Integer recognitionStatus;
    
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
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 商品详细信息（非数据库字段）
     */
    @TableField(exist = false)
    private YitongGoods goods;
}
