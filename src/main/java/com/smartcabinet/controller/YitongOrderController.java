package com.smartcabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.common.result.Result;
import com.smartcabinet.entity.YitongOrder;
import com.smartcabinet.entity.YitongOrderItem;
import com.smartcabinet.mapper.YitongOrderItemMapper;
import com.smartcabinet.mapper.YitongOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 易通无人柜订单控制器
 * 用于微信小程序订单查看功能
 * 
 * @author SmartCabinet Team
 */
@RestController
@RequestMapping("/yitong/orders")
@RequiredArgsConstructor
public class YitongOrderController {
    
    private final YitongOrderMapper orderMapper;
    private final YitongOrderItemMapper orderItemMapper;
    
    /**
     * 分页查询用户所有订单
     * GET /yitong/orders?userId=xxx&pageNum=1&pageSize=10
     */
    @GetMapping
    public Result<IPage<YitongOrder>> getUserOrders(
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Page<YitongOrder> page = new Page<>(pageNum, pageSize);
        IPage<YitongOrder> orderPage = orderMapper.selectUserOrdersPage(page, userId);
        
        return Result.success("查询用户订单成功", orderPage);
    }
    
    /**
     * 根据订单状态分页查询用户订单
     * GET /yitong/orders/status?userId=xxx&orderStatus=COMPLETED&pageNum=1&pageSize=10
     */
    @GetMapping("/status")
    public Result<IPage<YitongOrder>> getUserOrdersByStatus(
            @RequestParam String userId,
            @RequestParam String orderStatus,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Page<YitongOrder> page = new Page<>(pageNum, pageSize);
        IPage<YitongOrder> orderPage = orderMapper.selectUserOrdersByStatusPage(page, userId, orderStatus);
        
        return Result.success("查询用户订单成功", orderPage);
    }
    
    /**
     * 查询订单详情（包含商品明细）
     * GET /yitong/orders/detail/{orderCode}
     */
    @GetMapping("/detail/{orderCode}")
    public Result<YitongOrder> getOrderDetail(@PathVariable String orderCode) {
        
        // 查询订单基本信息
        YitongOrder order = orderMapper.selectOrderDetailByCode(orderCode);
        if (order == null) {
            return Result.error("订单不存在");
        }
        
        // 查询订单商品明细
        List<YitongOrderItem> orderItems = orderItemMapper.selectOrderItemsByCode(orderCode);
        order.setOrderItems(orderItems);
        
        return Result.success("查询订单详情成功", order);
    }
    
    /**
     * 获取用户订单统计信息
     * GET /yitong/orders/statistics?userId=xxx
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getUserOrderStatistics(@RequestParam String userId) {
        
        // 统计各状态订单数量
        List<Object> statusCounts = orderMapper.selectUserOrderStatusCount(userId);
        
        // 查询最近的订单
        List<YitongOrder> recentOrders = orderMapper.selectUserRecentOrders(userId, 5);
        
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("statusCounts", statusCounts);
        statistics.put("recentOrders", recentOrders);
        
        return Result.success("查询订单统计成功", statistics);
    }
    
    /**
     * 获取用户最近订单
     * GET /yitong/orders/recent?userId=xxx&limit=5
     */
    @GetMapping("/recent")
    public Result<List<YitongOrder>> getUserRecentOrders(
            @RequestParam String userId,
            @RequestParam(defaultValue = "5") Integer limit) {
        
        List<YitongOrder> recentOrders = orderMapper.selectUserRecentOrders(userId, limit);
        
        return Result.success("查询最近订单成功", recentOrders);
    }
    
    /**
     * 获取订单商品明细
     * GET /yitong/orders/{orderCode}/items
     */
    @GetMapping("/{orderCode}/items")
    public Result<List<YitongOrderItem>> getOrderItems(@PathVariable String orderCode) {
        
        List<YitongOrderItem> orderItems = orderItemMapper.selectOrderItemsByCode(orderCode);
        
        return Result.success("查询订单商品明细成功", orderItems);
    }
    
    /**
     * 查询用户待支付订单列表（分页）
     * GET /yitong/orders/pending-payment?userId=xxx&pageNum=1&pageSize=10
     * 用于微信小程序"待支付"标签页
     */
    @GetMapping("/pending-payment")
    public Result<IPage<YitongOrder>> getPendingPaymentOrders(
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Page<YitongOrder> page = new Page<>(pageNum, pageSize);
        // 查询待支付状态的订单
        IPage<YitongOrder> orderPage = orderMapper.selectUserOrdersByStatusPage(page, userId, "PENDING");
        
        return Result.success("查询待支付订单成功", orderPage);
    }
    
    /**
     * 查询用户售后退款订单列表（分页）
     * GET /yitong/orders/refund?userId=xxx&pageNum=1&pageSize=10
     * 用于微信小程序"售后退款"标签页
     */
    @GetMapping("/refund")
    public Result<IPage<YitongOrder>> getRefundOrders(
            @RequestParam String userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Page<YitongOrder> page = new Page<>(pageNum, pageSize);
        // 查询退款状态的订单
        IPage<YitongOrder> orderPage = orderMapper.selectUserOrdersByStatusPage(page, userId, "REFUNDED");
        
        return Result.success("查询售后退款订单成功", orderPage);
    }
    
    /**
     * 查询用户待支付订单数量
     * GET /yitong/orders/pending-payment/count?userId=xxx
     * 用于微信小程序显示待支付订单数量徽标
     */
    @GetMapping("/pending-payment/count")
    public Result<Integer> getPendingPaymentCount(@RequestParam String userId) {
        
        // 统计待支付订单数量
        Long count = orderMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<YitongOrder>()
                .eq("user_id", userId)
                .eq("order_status", "PENDING")
                .eq("deleted", 0));
        
        return Result.success("查询待支付订单数量成功", count.intValue());
    }
    
    /**
     * 查询用户售后退款订单数量
     * GET /yitong/orders/refund/count?userId=xxx
     * 用于微信小程序显示售后退款订单数量徽标
     */
    @GetMapping("/refund/count")
    public Result<Integer> getRefundCount(@RequestParam String userId) {
        
        // 统计退款订单数量
        Long count = orderMapper.selectCount(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<YitongOrder>()
                .eq("user_id", userId)
                .eq("order_status", "REFUNDED")
                .eq("deleted", 0));
        
        return Result.success("查询售后退款订单数量成功", count.intValue());
    }
}
