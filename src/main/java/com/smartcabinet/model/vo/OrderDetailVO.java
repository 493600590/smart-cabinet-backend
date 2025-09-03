package com.smartcabinet.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单详情VO - 用于小程序端展示订单详情
 * 
 * @author SmartCabinet Team
 */
@Data
public class OrderDetailVO {
    
    /**
     * 订单ID
     */
    private Long orderId;
    
    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 易通订单号
     */
    private String yitongOrderNo;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * 设备位置
     */
    private String deviceLocation;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 用户昵称
     */
    private String userNickname;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    
    /**
     * 商品金额
     */
    private BigDecimal productAmount;
    
    /**
     * 优惠金额
     */
    private BigDecimal promotionAmount;
    
    /**
     * 实付金额
     */
    private BigDecimal actualAmount;
    
    /**
     * 订单状态
     */
    private String orderStatus;
    
    /**
     * 支付状态
     */
    private String payStatus;
    
    /**
     * 支付类型
     */
    private Integer payType;
    
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 订单商品明细
     */
    private List<OrderItemVO> orderItems;
} 