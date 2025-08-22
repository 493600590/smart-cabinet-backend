package com.smartcabinet.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 易通无人柜设备扩展实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hibianli_devices")
public class YitongDevice {
    
    /**
     * 设备ID
     */
    @TableId(value = "device_id", type = IdType.AUTO)
    private Long deviceId;
    
    /**
     * 设备编码
     */
    @TableField("device_code")
    private String deviceCode;
    
    /**
     * 外部编码
     */
    @TableField("ext_code")
    private String extCode;
    
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;
    
    /**
     * 设备型号
     */
    @TableField("device_model")
    private String deviceModel;
    
    /**
     * 网络状态 0-未知 1-在线 2-离线
     */
    @TableField("net_status")
    private Integer netStatus;
    
    /**
     * 电源状态 0-未知 1-通电 2-断电
     */
    @TableField("ele_status")
    private Integer eleStatus;
    
    /**
     * 门状态 0-未知 1-开门 2-关门
     */
    @TableField("door_status")
    private Integer doorStatus;
    
    /**
     * 是否锁定 0-未锁定 1-锁定
     */
    @TableField("is_forbidden")
    private Integer isForbidden;
    
    /**
     * 设备位置
     */
    @TableField("location")
    private String location;
    
    /**
     * 详细地址
     */
    @TableField("address")
    private String address;
    
    /**
     * 商户ID
     */
    @TableField("merchant_id")
    private Integer merchantId;
    
    /**
     * 公司ID
     */
    @TableField("company_id")
    private Integer companyId;
    
    /**
     * 最后心跳时间
     */
    @TableField("last_heartbeat")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastHeartbeat;
    
    /**
     * 系统音量(0-100)
     */
    @TableField("system_volume")
    private Integer systemVolume;
    
    /**
     * 广告音量(0-100)
     */
    @TableField("media_volume")
    private Integer mediaVolume;
    
    /**
     * 未开门落锁时间(秒)
     */
    @TableField("not_open_lock_delay")
    private Integer notOpenLockDelay;
    
    /**
     * 固件版本
     */
    @TableField("firmware_version")
    private String firmwareVersion;
    
    /**
     * APP版本
     */
    @TableField("app_version")
    private String appVersion;
    
    /**
     * 预留字段1
     */
    @TableField("reserved_field1")
    private String reservedField1;
    
    /**
     * 预留字段2
     */
    @TableField("reserved_field2")
    private String reservedField2;
    
    /**
     * 预留字段3
     */
    @TableField("reserved_field3")
    private String reservedField3;
    
    /**
     * 预留字段4
     */
    @TableField("reserved_field4")
    private String reservedField4;
    
    /**
     * 是否删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
