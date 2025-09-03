package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.model.entity.YitongOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 易通订单数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface YitongOrderMapper extends BaseMapper<YitongOrder> {
    
    /**
     * 分页查询用户订单
     */
    Page<YitongOrder> selectUserOrderPage(Page<YitongOrder> page, @Param("userId") String userId, @Param("orderStatus") String orderStatus);
    
    /**
     * 根据订单编号查询订单
     */
    YitongOrder selectByOrderCode(@Param("orderCode") String orderCode);
    
    /**
     * 统计用户订单数量
     */
    int countUserOrders(@Param("userId") String userId, @Param("orderStatus") String orderStatus);
    
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
    List<YitongOrder> selectTimeoutOrders(@Param("timeoutMinutes") Integer timeoutMinutes);
    
    /**
     * 分页查询用户订单（不分状态）
     */
    Page<YitongOrder> selectUserOrdersPage(Page<YitongOrder> page, @Param("userId") String userId);
    
    /**
     * 根据订单状态分页查询用户订单
     */
    Page<YitongOrder> selectUserOrdersByStatusPage(Page<YitongOrder> page, @Param("userId") String userId, @Param("orderStatus") String orderStatus);
    
    /**
     * 根据订单编号查询订单详情
     */
    YitongOrder selectOrderDetailByCode(@Param("orderCode") String orderCode);
    
    /**
     * 统计用户各状态订单数量
     */
    List<Object> selectUserOrderStatusCount(@Param("userId") String userId);
    
    /**
     * 查询用户最近订单
     */
    List<YitongOrder> selectUserRecentOrders(@Param("userId") String userId, @Param("limit") Integer limit);
}
