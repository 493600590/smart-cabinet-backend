package com.smartcabinet.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * @author SmartCabinet Team
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
public class User {
    
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    
    /**
     * 密码哈希
     */
    @TableField("password_hash")
    @JsonIgnore
    private String passwordHash;
    
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
    /**
     * 微信OpenID
     */
    @TableField("openid")
    private String openid;
    
    /**
     * 头像URL
     */
    @TableField("avatar")
    private String avatar;
    
    /**
     * 信用分
     */
    @TableField("credit_score")
    private Integer creditScore;
    
    /**
     * 用户角色
     */
    @TableField("role")
    private String role;
    
    /**
     * 账户状态 0-正常 1-锁定
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("deleted")
    @TableLogic
    @JsonIgnore
    private Integer deleted;
    
    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
