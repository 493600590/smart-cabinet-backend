package com.smartcabinet.controller;

import com.smartcabinet.SmartCabinetApplication;
import com.smartcabinet.service.YitongOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单控制器测试类
 */
@SpringBootTest(classes = SmartCabinetApplication.class)
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    private YitongOrderService yitongOrderService;

    @Test
    public void testGetUserOrders() {
        // 测试查询用户订单
        assertNotNull(yitongOrderService);
        
        // 查询用户ID为YT_USER_001的订单
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.smartcabinet.model.entity.YitongOrder> page = yitongOrderService.getUserOrderPage(1, 10, "YT_USER_001", null);
        assertNotNull(page);
        assertNotNull(page.getRecords());
        
        System.out.println("查询到的订单数量: " + page.getTotal());
        System.out.println("当前页订单数量: " + page.getRecords().size());
        
        if (!page.getRecords().isEmpty()) {
            com.smartcabinet.model.entity.YitongOrder firstOrder = page.getRecords().get(0);
            System.out.println("第一个订单: " + firstOrder.getOrderCode() + ", 状态: " + firstOrder.getOrderStatus());
        }
    }

    @Test
    public void testGetOrderByOrderCode() {
        // 测试根据订单号查询订单
        com.smartcabinet.model.entity.YitongOrder order = yitongOrderService.getByOrderCode("ORD20241218001");
        assertNotNull(order);
        assertEquals("ORD20241218001", order.getOrderCode());
        assertEquals("YT_USER_001", order.getUserId());
        assertEquals("DEV001", order.getDeviceCode());
        
        System.out.println("查询到的订单: " + order.getOrderCode() + ", 金额: " + order.getActualAmount());
    }
} 