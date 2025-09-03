package com.smartcabinet.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单商品明细VO - 用于小程序端展示订单商品明细
 * 
 * @author SmartCabinet Team
 */
@Data
public class OrderItemVO {
    
    /**
     * 订单商品ID
     */
    private Long itemId;
    
    /**
     * 商品ID
     */
    private Long goodsId;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 商品规格
     */
    private String goodsSku;
    
    /**
     * 商品图片
     */
    private String goodsImage;
    
    /**
     * 商品单价
     */
    private BigDecimal unitPrice;
    
    /**
     * 购买数量
     */
    private Integer quantity;
    
    /**
     * 小计金额
     */
    private BigDecimal subtotal;
    
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    
    /**
     * 实付金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 商品状态
     */
    private Integer goodsStatus;
    
    /**
     * 识别状态
     */
    private Integer recognitionStatus;
    
    /**
     * 支付时间
     */
    private String payTime;
} 