package com.smartcabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.common.result.Result;
import com.smartcabinet.model.entity.YitongOrder;
import com.smartcabinet.mapper.YitongOrderMapper;
import com.smartcabinet.mapper.YitongOrderItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 易通无人柜订单控制器测试类
 * 
 * @author SmartCabinet Team
 */
@ExtendWith(MockitoExtension.class)
class YitongOrderControllerTest {
    
    @Mock
    private YitongOrderMapper orderMapper;
    
    @Mock
    private YitongOrderItemMapper orderItemMapper;
    
    @InjectMocks
    private YitongOrderController orderController;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    
    @Test
    void testGetPendingPaymentOrders() throws Exception {
        // 准备测试数据
        String userId = "YT_USER_001";
        Page<YitongOrder> page = new Page<>(1, 10);
        List<YitongOrder> orders = new ArrayList<>();
        
        YitongOrder order = new YitongOrder();
        order.setOrderId(1L);
        order.setOrderCode("ORD20241218001");
        order.setUserId(userId);
        order.setOrderAmount(new BigDecimal("12.50"));
        order.setActualAmount(new BigDecimal("12.50"));
        order.setOrderStatus("PENDING");
        order.setPayStatus("UNPAID");
        order.setCreatedAt(LocalDateTime.now());
        orders.add(order);
        
        page.setRecords(orders);
        page.setTotal(1);
        
        // Mock方法调用
        when(orderMapper.selectUserOrdersByStatusPage(any(Page.class), eq(userId), eq("PENDING")))
            .thenReturn(page);
        
        // 执行测试
        mockMvc.perform(get("/yitong/orders/pending-payment")
                .param("userId", userId)
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询待支付订单成功"))
                .andExpect(jsonPath("$.data.records").isArray())
                .andExpect(jsonPath("$.data.total").value(1));
    }
    
    @Test
    void testGetRefundOrders() throws Exception {
        // 准备测试数据
        String userId = "YT_USER_001";
        Page<YitongOrder> page = new Page<>(1, 10);
        List<YitongOrder> orders = new ArrayList<>();
        
        YitongOrder order = new YitongOrder();
        order.setOrderId(2L);
        order.setOrderCode("ORD20241218002");
        order.setUserId(userId);
        order.setOrderAmount(new BigDecimal("8.00"));
        order.setActualAmount(new BigDecimal("8.00"));
        order.setOrderStatus("REFUNDED");
        order.setPayStatus("REFUNDED");
        order.setCreatedAt(LocalDateTime.now());
        orders.add(order);
        
        page.setRecords(orders);
        page.setTotal(1);
        
        // Mock方法调用
        when(orderMapper.selectUserOrdersByStatusPage(any(Page.class), eq(userId), eq("REFUNDED")))
            .thenReturn(page);
        
        // 执行测试
        mockMvc.perform(get("/yitong/orders/refund")
                .param("userId", userId)
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询售后退款订单成功"))
                .andExpect(jsonPath("$.data.records").isArray())
                .andExpect(jsonPath("$.data.total").value(1));
    }
    
    @Test
    void testGetPendingPaymentCount() throws Exception {
        // 准备测试数据
        String userId = "YT_USER_001";
        Long count = 2L;
        
        // Mock方法调用
        when(orderMapper.selectCount(any())).thenReturn(count);
        
        // 执行测试
        mockMvc.perform(get("/yitong/orders/pending-payment/count")
                .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询待支付订单数量成功"))
                .andExpect(jsonPath("$.data").value(2));
    }
    
    @Test
    void testGetRefundCount() throws Exception {
        // 准备测试数据
        String userId = "YT_USER_001";
        Long count = 1L;
        
        // Mock方法调用
        when(orderMapper.selectCount(any())).thenReturn(count);
        
        // 执行测试
        mockMvc.perform(get("/yitong/orders/refund/count")
                .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询售后退款订单数量成功"))
                .andExpect(jsonPath("$.data").value(1));
    }
} 