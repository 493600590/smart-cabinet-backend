package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 订单数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 分页查询用户订单
     */
    Page<Order> selectUserOrderPage(Page<Order> page, @Param("userId") Long userId, @Param("status") String status);
    
    /**
     * 根据订单编号查询订单
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 统计用户订单数量
     */
    int countUserOrders(@Param("userId") Long userId, @Param("status") String status);
    
    /**
     * 统计指定时间范围内的销售额
     */
    BigDecimal sumSalesAmount(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计每日销售数据
     */
    List<Map<String, Object>> selectDailySalesStats(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    /**
     * 查询超时未支付订单
     */
    List<Order> selectTimeoutOrders(@Param("timeoutMinutes") Integer timeoutMinutes);
}
