package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("SELECT * FROM users WHERE phone = #{phone} AND deleted = 0 LIMIT 1")
    User selectByPhone(@Param("phone") String phone);
    
    /**
     * 根据微信OpenID查询用户
     */
    @Select("SELECT * FROM users WHERE openid = #{openid} AND deleted = 0 LIMIT 1")
    User selectByOpenid(@Param("openid") String openid);
    
    /**
     * 更新用户信用分
     */
    @Update("UPDATE users SET credit_score = #{creditScore}, updated_at = NOW() WHERE user_id = #{userId} AND deleted = 0")
    int updateCreditScore(@Param("userId") Long userId, @Param("creditScore") Integer creditScore);
}
