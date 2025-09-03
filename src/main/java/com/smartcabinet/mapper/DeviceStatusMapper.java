package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.model.entity.DeviceStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备状态记录数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface DeviceStatusMapper extends BaseMapper<DeviceStatus> {
    
    /**
     * 查询设备最新状态
     */
    DeviceStatus selectLatestByDeviceId(@Param("deviceId") String deviceId);
    
    /**
     * 查询设备历史状态
     */
    List<DeviceStatus> selectHistoryByDeviceId(@Param("deviceId") String deviceId, 
                                               @Param("startTime") LocalDateTime startTime, 
                                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 删除过期数据
     */
    int deleteExpiredData(@Param("expireTime") LocalDateTime expireTime);
}
