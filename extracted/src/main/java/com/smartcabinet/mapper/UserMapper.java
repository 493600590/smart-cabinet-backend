package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据手机号查询用户
     */
    User selectByPhone(@Param("phone") String phone);
    
    /**
     * 根据微信OpenID查询用户
     */
    User selectByOpenid(@Param("openid") String openid);
    
    /**
     * 更新用户信用分
     */
    int updateCreditScore(@Param("userId") Long userId, @Param("creditScore") Integer creditScore);
}
