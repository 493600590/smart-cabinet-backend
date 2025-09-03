package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.model.entity.YitongOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 易通订单服务接口
 * 
 * @author SmartCabinet Team
 */
public interface YitongOrderService extends IService<YitongOrder> {
    
    /**
     * 分页查询用户订单
     */
    Page<YitongOrder> getUserOrderPage(int pageNum, int pageSize, String userId, String orderStatus);
    
    /**
     * 根据订单编号查询订单
     */
    YitongOrder getByOrderCode(String orderCode);
    
    /**
     * 支付订单
     */
    boolean payOrder(String orderCode, String paymentMethod, String transactionId);
    
    /**
     * 取消订单
     */
    boolean cancelOrder(String orderCode);
    
    /**
     * 完成订单
     */
    boolean completeOrder(String orderCode);
    
    /**
     * 申请退款
     */
    boolean refundOrder(String orderCode, String reason);
    
    /**
     * 统计销售额
     */
    BigDecimal getSalesAmount(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取每日销售统计
     */
    List<Map<String, Object>> getDailySalesStats(String startDate, String endDate);
    
    /**
     * 处理超时订单
     */
    void handleTimeoutOrders();
    
    /**
     * 统计用户订单数量
     */
    int countUserOrders(String userId, String orderStatus);
} 