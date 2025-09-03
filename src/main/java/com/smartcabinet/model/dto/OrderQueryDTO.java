package com.smartcabinet.model.dto;

import lombok.Data;

/**
 * 订单查询DTO - 用于订单查询条件
 * 
 * @author SmartCabinet Team
 */
@Data
public class OrderQueryDTO {
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 订单状态
     */
    private String orderStatus;
    
    /**
     * 支付状态
     */
    private String payStatus;
    
    /**
     * 设备编码
     */
    private String deviceCode;
    
    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 开始时间
     */
    private String startTime;
    
    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 页码
     */
    private Integer pageNum = 1;
    
    /**
     * 页大小
     */
    private Integer pageSize = 10;
} 