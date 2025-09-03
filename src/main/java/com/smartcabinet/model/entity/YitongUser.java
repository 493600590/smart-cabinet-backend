package com.smartcabinet.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 易通无人柜用户扩展实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("yt_users")
public class YitongUser {
    
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    
    /**
     * 关联原用户表ID
     */
    @TableField("original_user_id")
    private Long originalUserId;
    
    /**
     * 易通用户ID
     */
    @TableField("hbl_user_id")
    private String yitongUserId;
    
    /**
     * 微信OpenID
     */
    @TableField("openid")
    private String openid;
    
    /**
     * 微信UnionID
     */
    @TableField("unionid")
    private String unionid;
    
    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;
    
    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;
    
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    
    /**
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;
    
    /**
     * 性别 0-未知 1-男 2-女
     */
    @TableField("gender")
    private Integer gender;
    
    /**
     * 生日
     */
    @TableField("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    
    /**
     * 注册来源
     */
    @TableField("register_source")
    private String registerSource;
    
    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;
    
    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;
    
    /**
     * 总订单数
     */
    @TableField("total_order_count")
    private Integer totalOrderCount;
    
    /**
     * 总订单金额
     */
    @TableField("total_order_amount")
    private BigDecimal totalOrderAmount;
    
    /**
     * 信用分
     */
    @TableField("credit_score")
    private Integer creditScore;
    
    /**
     * 用户等级
     */
    @TableField("user_level")
    private Integer userLevel;
    
    /**
     * 用户状态 1-正常 2-禁用
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 公司ID
     */
    @TableField("company_id")
    private Integer companyId;
    
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
     * 预留字段5
     */
    @TableField("reserved_field5")
    private String reservedField5;
    
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
