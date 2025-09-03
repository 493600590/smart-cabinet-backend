package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.model.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 设备数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    
    /**
     * 分页查询设备列表
     */
    Page<Device> selectDevicePage(Page<Device> page, @Param("status") String status, @Param("location") String location);
    
    /**
     * 更新设备心跳时间
     */
    int updateHeartbeat(@Param("deviceId") String deviceId, @Param("heartbeatTime") LocalDateTime heartbeatTime);
    
    /**
     * 查询离线设备
     */
    List<Device> selectOfflineDevices(@Param("timeoutMinutes") Integer timeoutMinutes);
    
    /**
     * 统计设备状态
     */
    List<Map<String, Object>> selectDeviceStatusStats();
    
    /**
     * 根据位置搜索设备
     */
    List<Device> selectByLocation(@Param("location") String location);
}
