package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.entity.OrderItem;

import java.util.List;

/**
 * 订单商品服务接口
 * 
 * @author SmartCabinet Team
 */
public interface OrderItemService extends IService<OrderItem> {
    
    /**
     * 根据订单ID查询订单商品
     */
    List<OrderItem> getByOrderId(Long orderId);
    
    /**
     * 批量保存订单商品
     */
    boolean saveBatch(List<OrderItem> items);
}
