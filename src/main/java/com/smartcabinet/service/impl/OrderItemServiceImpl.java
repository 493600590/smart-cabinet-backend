package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.model.entity.OrderItem;
import com.smartcabinet.mapper.OrderItemMapper;
import com.smartcabinet.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单商品服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    
    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }
    
    @Override
    public boolean saveBatch(List<OrderItem> items) {
        return baseMapper.insertBatch(items) > 0;
    }
}
