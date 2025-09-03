package com.smartcabinet.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.common.result.Result;
import com.smartcabinet.model.entity.Order;
import com.smartcabinet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 * 
 * @author SmartCabinet Team
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    
    private final OrderService orderService;
    
    /**
     * 创建订单
     */
    @PostMapping
    public Result<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setDeviceId(request.getDeviceId());
        
        Order createdOrder = orderService.createOrder(order, request.getItems());
        return Result.success(createdOrder);
    }
    
    /**
     * 分页查询用户订单
     */
    @GetMapping
    public Result<Page<Order>> getUserOrders(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam @NotNull Long userId,
            @RequestParam(required = false) String status) {
        Page<Order> page = orderService.getUserOrderPage(pageNum, pageSize, userId, status);
        return Result.success(page);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{orderNo}")
    public Result<Order> getOrder(@PathVariable @NotBlank String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
        return Result.success(order);
    }
    
    /**
     * 支付订单
     */
    @PutMapping("/{orderNo}/pay")
    public Result<Boolean> payOrder(@PathVariable @NotBlank String orderNo,
                                   @Valid @RequestBody PayOrderRequest request) {
        boolean success = orderService.payOrder(orderNo, request.getPaymentMethod(), request.getTransactionId());
        return Result.success(success);
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/{orderNo}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable @NotBlank String orderNo) {
        boolean success = orderService.cancelOrder(orderNo);
        return Result.success(success);
    }
    
    /**
     * 申请退款
     */
    @PostMapping("/{orderNo}/refund")
    public Result<Boolean> refundOrder(@PathVariable @NotBlank String orderNo,
                                      @Valid @RequestBody RefundOrderRequest request) {
        boolean success = orderService.refundOrder(orderNo, request.getReason());
        return Result.success(success);
    }
    
    /**
     * 创建订单请求对象
     */
    public static class CreateOrderRequest {
        @NotNull(message = "用户ID不能为空")
        private Long userId;
        
        @NotBlank(message = "设备ID不能为空")
        private String deviceId;
        
        @NotNull(message = "商品列表不能为空")
        private List<Map<String, Object>> items;
        
        // getters and setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getDeviceId() { return deviceId; }
        public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
        public List<Map<String, Object>> getItems() { return items; }
        public void setItems(List<Map<String, Object>> items) { this.items = items; }
    }
    
    /**
     * 支付订单请求对象
     */
    public static class PayOrderRequest {
        @NotBlank(message = "支付方式不能为空")
        private String paymentMethod;
        
        @NotBlank(message = "交易号不能为空")
        private String transactionId;
        
        // getters and setters
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }
    
    /**
     * 退款请求对象
     */
    public static class RefundOrderRequest {
        @NotBlank(message = "退款原因不能为空")
        private String reason;
        
        // getters and setters
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}
