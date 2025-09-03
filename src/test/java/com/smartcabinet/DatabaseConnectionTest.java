package com.smartcabinet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据库连接测试
 */
@SpringBootTest(classes = SmartCabinetApplication.class)
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        // 测试数据库连接
        assertNotNull(jdbcTemplate);
        
        // 查询yt_orders表中的数据数量
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM yt_orders", Integer.class);
        assertNotNull(count);
        assertTrue(count > 0);
        
        System.out.println("yt_orders表中的订单数量: " + count);
        
        // 查询一个具体的订单
        String orderCode = jdbcTemplate.queryForObject(
            "SELECT order_code FROM yt_orders LIMIT 1", String.class);
        assertNotNull(orderCode);
        
        System.out.println("查询到的订单号: " + orderCode);
    }
} 