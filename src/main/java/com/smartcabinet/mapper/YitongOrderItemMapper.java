package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.model.entity.YitongOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 易通无人柜订单商品明细Mapper接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface YitongOrderItemMapper extends BaseMapper<YitongOrderItem> {
    
    /**
     * 根据订单ID查询订单商品明细（包含商品信息）
     */
    @Select("SELECT oi.*, " +
            "g.name as goods_name, g.sku, g.description, g.image_url, " +
            "g.brand_name, g.weight, g.volume " +
            "FROM yt_order_items oi " +
            "LEFT JOIN yt_goods g ON oi.goods_id = g.goods_id " +
            "WHERE oi.order_id = #{orderId} " +
            "ORDER BY oi.created_at ASC")
    List<YitongOrderItem> selectOrderItemsWithGoods(@Param("orderId") Long orderId);
    
    /**
     * 根据订单编号查询订单商品明细
     */
    @Select("SELECT oi.*, " +
            "g.name as goods_name, g.sku, g.description, g.image_url, " +
            "g.brand_name, g.weight, g.volume " +
            "FROM yt_order_items oi " +
            "LEFT JOIN yt_goods g ON oi.goods_id = g.goods_id " +
            "WHERE oi.order_code = #{orderCode} " +
            "ORDER BY oi.created_at ASC")
    List<YitongOrderItem> selectOrderItemsByCode(@Param("orderCode") String orderCode);
    
    /**
     * 统计用户购买的商品种类和数量
     */
    @Select("SELECT oi.goods_id, g.name, g.image_url, " +
            "SUM(oi.quantity) as total_quantity, " +
            "COUNT(DISTINCT oi.order_id) as order_count, " +
            "MAX(oi.created_at) as last_buy_time " +
            "FROM yt_order_items oi " +
            "LEFT JOIN yt_orders o ON oi.order_id = o.order_id " +
            "LEFT JOIN yt_goods g ON oi.goods_id = g.goods_id " +
            "WHERE o.user_id = #{userId} " +
            "AND o.order_status = 'COMPLETED' " +
            "AND o.deleted = 0 " +
            "GROUP BY oi.goods_id, g.name, g.image_url " +
            "ORDER BY total_quantity DESC")
    List<Object> selectUserGoodsStatistics(@Param("userId") String userId);
}
