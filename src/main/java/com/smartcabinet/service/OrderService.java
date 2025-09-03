package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.model.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 * 
 * @author SmartCabinet Team
 */
public interface OrderService extends IService<Order> {
    
    /**
     * 创建订单
     */
    Order createOrder(Order order, List<Map<String, Object>> items);
    
    /**
     * 分页查询用户订单
     */
    Page<Order> getUserOrderPage(int pageNum, int pageSize, Long userId, String status);
    
    /**
     * 根据订单编号查询订单
     */
    Order getByOrderNo(String orderNo);
    
    /**
     * 支付订单
     */
    boolean payOrder(String orderNo, String paymentMethod, String transactionId);
    
    /**
     * 取消订单
     */
    boolean cancelOrder(String orderNo);
    
    /**
     * 完成订单
     */
    boolean completeOrder(String orderNo);
    
    /**
     * 申请退款
     */
    boolean refundOrder(String orderNo, String reason);
    
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
    int countUserOrders(Long userId, String status);
}
