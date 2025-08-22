package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.entity.YitongOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 易通无人柜订单Mapper接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface YitongOrderMapper extends BaseMapper<YitongOrder> {
    
    /**
     * 分页查询用户订单列表（包含订单商品信息）
     */
    @Select("SELECT o.*, " +
            "d.device_name, d.location, d.address, " +
            "u.nickname, u.avatar_url " +
            "FROM hibianli_orders o " +
            "LEFT JOIN hibianli_devices d ON o.device_code = d.device_code " +
            "LEFT JOIN hibianli_users u ON o.user_id = u.hbl_user_id " +
            "WHERE o.user_id = #{userId} " +
            "AND o.deleted = 0 " +
            "ORDER BY o.created_at DESC")
    IPage<YitongOrder> selectUserOrdersPage(Page<YitongOrder> page, @Param("userId") String userId);
    
    /**
     * 根据订单状态分页查询用户订单
     */
    @Select("SELECT o.*, " +
            "d.device_name, d.location, d.address, " +
            "u.nickname, u.avatar_url " +
            "FROM hibianli_orders o " +
            "LEFT JOIN hibianli_devices d ON o.device_code = d.device_code " +
            "LEFT JOIN hibianli_users u ON o.user_id = u.hbl_user_id " +
            "WHERE o.user_id = #{userId} " +
            "AND o.order_status = #{orderStatus} " +
            "AND o.deleted = 0 " +
            "ORDER BY o.created_at DESC")
    IPage<YitongOrder> selectUserOrdersByStatusPage(Page<YitongOrder> page, 
                                                      @Param("userId") String userId, 
                                                      @Param("orderStatus") String orderStatus);
    
    /**
     * 根据订单编号查询订单详情（包含所有关联信息）
     */
    @Select("SELECT o.*, " +
            "d.device_name, d.location, d.address, d.device_model, " +
            "u.nickname, u.avatar_url, u.phone " +
            "FROM hibianli_orders o " +
            "LEFT JOIN hibianli_devices d ON o.device_code = d.device_code " +
            "LEFT JOIN hibianli_users u ON o.user_id = u.hbl_user_id " +
            "WHERE o.order_code = #{orderCode} " +
            "AND o.deleted = 0")
    YitongOrder selectOrderDetailByCode(@Param("orderCode") String orderCode);
    
    /**
     * 统计用户各状态订单数量
     */
    @Select("SELECT order_status, COUNT(*) as count " +
            "FROM hibianli_orders " +
            "WHERE user_id = #{userId} " +
            "AND deleted = 0 " +
            "GROUP BY order_status")
    List<Object> selectUserOrderStatusCount(@Param("userId") String userId);
    
    /**
     * 查询用户最近的订单
     */
    @Select("SELECT * FROM hibianli_orders " +
            "WHERE user_id = #{userId} " +
            "AND deleted = 0 " +
            "ORDER BY created_at DESC " +
            "LIMIT #{limit}")
    List<YitongOrder> selectUserRecentOrders(@Param("userId") String userId, @Param("limit") Integer limit);
}
