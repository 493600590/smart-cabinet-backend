package com.smartcabinet.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.common.result.Result;
import com.smartcabinet.entity.Device;
import com.smartcabinet.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 设备控制器
 * 
 * @author SmartCabinet Team
 */
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@Validated
public class DeviceController {
    
    private final DeviceService deviceService;
    
    /**
     * 分页查询设备
     */
    @GetMapping
    public Result<Page<Device>> getDevices(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String location) {
        Page<Device> page = deviceService.getDevicePage(pageNum, pageSize, status, location);
        return Result.success(page);
    }
    
    /**
     * 获取设备详情
     */
    @GetMapping("/{deviceId}")
    public Result<Device> getDevice(@PathVariable @NotBlank String deviceId) {
        Device device = deviceService.getById(deviceId);
        return Result.success(device);
    }
    
    /**
     * 获取设备状态
     */
    @GetMapping("/{deviceId}/status")
    public Result<Map<String, Object>> getDeviceStatus(@PathVariable @NotBlank String deviceId) {
        Map<String, Object> status = deviceService.getDeviceStatus(deviceId);
        return Result.success(status);
    }
    
    /**
     * 发送开门指令
     */
    @PostMapping("/{deviceId}/open-door")
    public Result<Boolean> openDoor(@PathVariable @NotBlank String deviceId,
                                   @RequestBody OpenDoorRequest request) {
        boolean success = deviceService.sendOpenDoorCommand(deviceId, request.getDoorId());
        return Result.success(success);
    }
    
    /**
     * 发送关门指令
     */
    @PostMapping("/{deviceId}/close-door")
    public Result<Boolean> closeDoor(@PathVariable @NotBlank String deviceId,
                                    @RequestBody CloseDoorRequest request) {
        boolean success = deviceService.sendCloseDoorCommand(deviceId, request.getDoorId());
        return Result.success(success);
    }
    
    /**
     * 更新设备心跳
     */
    @PutMapping("/{deviceId}/heartbeat")
    public Result<Boolean> updateHeartbeat(@PathVariable @NotBlank String deviceId) {
        boolean success = deviceService.updateHeartbeat(deviceId);
        return Result.success(success);
    }
    
    /**
     * 注册设备（管理员）
     */
    @PostMapping
    public Result<Device> registerDevice(@Valid @RequestBody Device device) {
        Device registeredDevice = deviceService.registerDevice(device);
        return Result.success(registeredDevice);
    }
    
    /**
     * 更新设备信息（管理员）
     */
    @PutMapping("/{deviceId}")
    public Result<Boolean> updateDevice(@PathVariable @NotBlank String deviceId,
                                       @Valid @RequestBody Device device) {
        device.setDeviceId(deviceId);
        boolean success = deviceService.updateDevice(device);
        return Result.success(success);
    }
    
    /**
     * 删除设备（管理员）
     */
    @DeleteMapping("/{deviceId}")
    public Result<Boolean> deleteDevice(@PathVariable @NotBlank String deviceId) {
        boolean success = deviceService.deleteDevice(deviceId);
        return Result.success(success);
    }
    
    /**
     * 获取离线设备
     */
    @GetMapping("/offline")
    public Result<List<Device>> getOfflineDevices(@RequestParam(defaultValue = "30") Integer timeoutMinutes) {
        List<Device> devices = deviceService.getOfflineDevices(timeoutMinutes);
        return Result.success(devices);
    }
    
    /**
     * 设备状态统计
     */
    @GetMapping("/stats")
    public Result<List<Map<String, Object>>> getDeviceStats() {
        List<Map<String, Object>> stats = deviceService.getDeviceStatusStats();
        return Result.success(stats);
    }
    
    /**
     * 根据位置搜索设备
     */
    @GetMapping("/search")
    public Result<List<Device>> searchDevices(@RequestParam @NotBlank String location) {
        List<Device> devices = deviceService.searchByLocation(location);
        return Result.success(devices);
    }
    
    /**
     * 开门请求对象
     */
    public static class OpenDoorRequest {
        @NotBlank(message = "门ID不能为空")
        private String doorId;
        
        // getters and setters
        public String getDoorId() { return doorId; }
        public void setDoorId(String doorId) { this.doorId = doorId; }
    }
    
    /**
     * 关门请求对象
     */
    public static class CloseDoorRequest {
        @NotBlank(message = "门ID不能为空")
        private String doorId;
        
        // getters and setters
        public String getDoorId() { return doorId; }
        public void setDoorId(String doorId) { this.doorId = doorId; }
    }
}
