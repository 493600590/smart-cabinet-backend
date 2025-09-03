package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.model.entity.YitongOrder;
import com.smartcabinet.mapper.YitongOrderMapper;
import com.smartcabinet.service.YitongOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 易通订单服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class YitongOrderServiceImpl extends ServiceImpl<YitongOrderMapper, YitongOrder> implements YitongOrderService {
    
    @Override
    public Page<YitongOrder> getUserOrderPage(int pageNum, int pageSize, String userId, String orderStatus) {
        Page<YitongOrder> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectUserOrderPage(page, userId, orderStatus);
    }
    
    @Override
    public YitongOrder getByOrderCode(String orderCode) {
        return baseMapper.selectByOrderCode(orderCode);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String orderCode, String paymentMethod, String transactionId) {
        YitongOrder order = getByOrderCode(orderCode);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("订单状态不正确");
        }
        
        order.setOrderStatus("PAID");
        order.setPayStatus("PAID");
        order.setThirdPayOrderId(transactionId);
        order.setTradeTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单支付成功，订单号：{}", orderCode);
        }
        return success;
    }
    
    @Override
    public boolean cancelOrder(String orderCode) {
        YitongOrder order = getByOrderCode(orderCode);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PENDING".equals(order.getOrderStatus())) {
            throw new RuntimeException("只能取消待处理订单");
        }
        
        order.setOrderStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单取消成功，订单号：{}", orderCode);
        }
        return success;
    }
    
    @Override
    public boolean completeOrder(String orderCode) {
        YitongOrder order = getByOrderCode(orderCode);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PAID".equals(order.getOrderStatus())) {
            throw new RuntimeException("只能完成已支付订单");
        }
        
        order.setOrderStatus("COMPLETED");
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单完成，订单号：{}", orderCode);
        }
        return success;
    }
    
    @Override
    public boolean refundOrder(String orderCode, String reason) {
        YitongOrder order = getByOrderCode(orderCode);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PAID".equals(order.getOrderStatus()) && !"COMPLETED".equals(order.getOrderStatus())) {
            throw new RuntimeException("订单状态不支持退款");
        }
        
        order.setOrderStatus("REFUNDED");
        order.setPayStatus("REFUNDED");
        order.setRemark(reason);
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单退款成功，订单号：{}，原因：{}", orderCode, reason);
        }
        return success;
    }
    
    @Override
    public BigDecimal getSalesAmount(LocalDateTime startTime, LocalDateTime endTime) {
        return baseMapper.sumSalesAmount(startTime, endTime);
    }
    
    @Override
    public List<Map<String, Object>> getDailySalesStats(String startDate, String endDate) {
        return baseMapper.selectDailySalesStats(startDate, endDate);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTimeoutOrders() {
        List<YitongOrder> timeoutOrders = baseMapper.selectTimeoutOrders(30); // 30分钟超时
        for (YitongOrder order : timeoutOrders) {
            cancelOrder(order.getOrderCode());
        }
    }
    
    @Override
    public int countUserOrders(String userId, String orderStatus) {
        return baseMapper.countUserOrders(userId, orderStatus);
    }
} 