package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * 设备服务接口
 * 
 * @author SmartCabinet Team
 */
public interface DeviceService extends IService<Device> {
    
    /**
     * 分页查询设备
     */
    Page<Device> getDevicePage(int pageNum, int pageSize, String status, String location);
    
    /**
     * 更新设备心跳
     */
    boolean updateHeartbeat(String deviceId);
    
    /**
     * 获取离线设备
     */
    List<Device> getOfflineDevices(Integer timeoutMinutes);
    
    /**
     * 统计设备状态
     */
    List<Map<String, Object>> getDeviceStatusStats();
    
    /**
     * 注册设备
     */
    Device registerDevice(Device device);
    
    /**
     * 更新设备信息
     */
    boolean updateDevice(Device device);
    
    /**
     * 删除设备
     */
    boolean deleteDevice(String deviceId);
    
    /**
     * 发送开门指令
     */
    boolean sendOpenDoorCommand(String deviceId, String doorId);
    
    /**
     * 发送关门指令
     */
    boolean sendCloseDoorCommand(String deviceId, String doorId);
    
    /**
     * 获取设备状态
     */
    Map<String, Object> getDeviceStatus(String deviceId);
    
    /**
     * 设备故障报警
     */
    void deviceFaultAlert(String deviceId, String faultType, String description);
    
    /**
     * 根据位置搜索设备
     */
    List<Device> searchByLocation(String location);
}
