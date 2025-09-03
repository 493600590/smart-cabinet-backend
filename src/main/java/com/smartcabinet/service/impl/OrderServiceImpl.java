package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.model.entity.Order;
import com.smartcabinet.model.entity.OrderItem;
import com.smartcabinet.mapper.OrderMapper;
import com.smartcabinet.service.OrderService;
import com.smartcabinet.service.OrderItemService;
import com.smartcabinet.service.ProductService;
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
 * 订单服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    private final OrderItemService orderItemService;
    private final ProductService productService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order, List<Map<String, Object>> items) {
        // 生成订单编号
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus("PENDING_PAYMENT");
        
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Map<String, Object> item : items) {
            Long productId = Long.valueOf(item.get("productId").toString());
            Integer quantity = Integer.valueOf(item.get("quantity").toString());
            
            // 检查库存并减少库存
            boolean stockReduced = productService.decreaseStock(productId, quantity);
            if (!stockReduced) {
                throw new RuntimeException("库存不足，商品ID：" + productId);
            }
            
            // 计算小计
            BigDecimal price = new BigDecimal(item.get("price").toString());
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(subtotal);
        }
        
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        // 保存订单
        save(order);
        
        // 保存订单商品
        for (Map<String, Object> item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(Long.valueOf(item.get("productId").toString()));
            orderItem.setProductName(item.get("productName").toString());
            orderItem.setProductPrice(new BigDecimal(item.get("price").toString()));
            orderItem.setQuantity(Integer.valueOf(item.get("quantity").toString()));
            orderItem.setSubtotal(orderItem.getProductPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            orderItemService.save(orderItem);
        }
        
        log.info("订单创建成功，订单号：{}", orderNo);
        return order;
    }
    
    @Override
    public Page<Order> getUserOrderPage(int pageNum, int pageSize, Long userId, String status) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectUserOrderPage(page, userId, status);
    }
    
    @Override
    public Order getByOrderNo(String orderNo) {
        return baseMapper.selectByOrderNo(orderNo);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String orderNo, String paymentMethod, String transactionId) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PENDING_PAYMENT".equals(order.getStatus())) {
            throw new RuntimeException("订单状态不正确");
        }
        
        order.setStatus("PAID");
        order.setPaymentMethod(paymentMethod);
        order.setPaymentTransactionId(transactionId);
        order.setPaymentTime(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单支付成功，订单号：{}", orderNo);
        }
        return success;
    }
    
    @Override
    public boolean cancelOrder(String orderNo) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PENDING_PAYMENT".equals(order.getStatus())) {
            throw new RuntimeException("只能取消待支付订单");
        }
        
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单取消成功，订单号：{}", orderNo);
        }
        return success;
    }
    
    @Override
    public boolean completeOrder(String orderNo) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PAID".equals(order.getStatus())) {
            throw new RuntimeException("只能完成已支付订单");
        }
        
        order.setStatus("COMPLETED");
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单完成，订单号：{}", orderNo);
        }
        return success;
    }
    
    @Override
    public boolean refundOrder(String orderNo, String reason) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        if (!"PAID".equals(order.getStatus()) && !"COMPLETED".equals(order.getStatus())) {
            throw new RuntimeException("订单状态不支持退款");
        }
        
        order.setStatus("REFUNDED");
        order.setRemark(reason);
        order.setUpdatedAt(LocalDateTime.now());
        
        boolean success = updateById(order);
        if (success) {
            log.info("订单退款成功，订单号：{}，原因：{}", orderNo, reason);
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
        List<Order> timeoutOrders = baseMapper.selectTimeoutOrders(30); // 30分钟超时
        for (Order order : timeoutOrders) {
            cancelOrder(order.getOrderNo());
        }
        log.info("处理超时订单完成，共处理{}个订单", timeoutOrders.size());
    }
    
    @Override
    public int countUserOrders(Long userId, String status) {
        return baseMapper.countUserOrders(userId, status);
    }
    
    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int) (Math.random() * 1000));
        return "ORD" + timestamp + String.format("%03d", Integer.parseInt(random));
    }
}
