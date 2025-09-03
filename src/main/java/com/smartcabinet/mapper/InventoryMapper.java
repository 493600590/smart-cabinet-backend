package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.model.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
    
    /**
     * 根据设备ID查询库存
     */
    List<Inventory> selectByDeviceId(@Param("deviceId") String deviceId);
    
    /**
     * 根据设备ID和商品ID查询库存
     */
    Inventory selectByDeviceAndProduct(@Param("deviceId") String deviceId, @Param("productId") Long productId);
    
    /**
     * 更新库存数量
     */
    int updateStock(@Param("inventoryId") Long inventoryId, @Param("quantity") Integer quantity);
    
    /**
     * 查询低库存预警
     */
    List<Inventory> selectLowStockInventory();
    
    /**
     * 统计设备总库存
     */
    Integer sumDeviceStock(@Param("deviceId") String deviceId);
}
