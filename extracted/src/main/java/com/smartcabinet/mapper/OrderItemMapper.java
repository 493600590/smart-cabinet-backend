package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单商品数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    
    /**
     * 根据订单ID查询订单商品
     */
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);
    
    /**
     * 批量插入订单商品
     */
    int insertBatch(@Param("items") List<OrderItem> items);
}
