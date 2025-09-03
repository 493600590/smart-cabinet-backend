package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.model.entity.Device;
import com.smartcabinet.mapper.DeviceMapper;
import com.smartcabinet.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    
    @Override
    public Page<Device> getDevicePage(int pageNum, int pageSize, String status, String location) {
        Page<Device> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectDevicePage(page, status, location);
    }
    
    @Override
    public boolean updateHeartbeat(String deviceId) {
        int result = baseMapper.updateHeartbeat(deviceId, LocalDateTime.now());
        if (result > 0) {
            log.debug("设备心跳更新成功，设备ID：{}", deviceId);
            return true;
        }
        return false;
    }
    
    @Override
    public List<Device> getOfflineDevices(Integer timeoutMinutes) {
        return baseMapper.selectOfflineDevices(timeoutMinutes);
    }
    
    @Override
    public List<Map<String, Object>> getDeviceStatusStats() {
        return baseMapper.selectDeviceStatusStats();
    }
    
    @Override
    public Device registerDevice(Device device) {
        device.setStatus("OFFLINE");
        device.setCreatedAt(LocalDateTime.now());
        device.setUpdatedAt(LocalDateTime.now());
        
        save(device);
        log.info("设备注册成功，设备ID：{}", device.getDeviceId());
        return device;
    }
    
    @Override
    public boolean updateDevice(Device device) {
        device.setUpdatedAt(LocalDateTime.now());
        return updateById(device);
    }
    
    @Override
    public boolean deleteDevice(String deviceId) {
        boolean result = removeById(deviceId);
        if (result) {
            log.info("设备删除成功，设备ID：{}", deviceId);
        }
        return result;
    }
    
    @Override
    public boolean sendOpenDoorCommand(String deviceId, String doorId) {
        // TODO: 通过MQTT发送开门指令
        log.info("发送开门指令，设备ID：{}，门ID：{}", deviceId, doorId);
        return true;
    }
    
    @Override
    public boolean sendCloseDoorCommand(String deviceId, String doorId) {
        // TODO: 通过MQTT发送关门指令
        log.info("发送关门指令，设备ID：{}，门ID：{}", deviceId, doorId);
        return true;
    }
    
    @Override
    public Map<String, Object> getDeviceStatus(String deviceId) {
        Device device = getById(deviceId);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }
        
        Map<String, Object> status = new HashMap<>();
        status.put("deviceId", device.getDeviceId());
        status.put("status", device.getStatus());
        status.put("lastHeartbeat", device.getLastHeartbeat());
        status.put("location", device.getLocation());
        // TODO: 添加更多状态信息
        
        return status;
    }
    
    @Override
    public void deviceFaultAlert(String deviceId, String faultType, String description) {
        log.warn("设备故障报警，设备ID：{}，故障类型：{}，描述：{}", deviceId, faultType, description);
        // TODO: 发送告警通知
    }
    
    @Override
    public List<Device> searchByLocation(String location) {
        return baseMapper.selectByLocation(location);
    }
}
