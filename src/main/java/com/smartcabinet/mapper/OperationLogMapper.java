package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.model.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    
    /**
     * 分页查询操作日志
     */
    Page<OperationLog> selectLogPage(Page<OperationLog> page, 
                                    @Param("userId") Long userId, 
                                    @Param("operationType") String operationType,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据用户ID查询操作日志
     */
    List<OperationLog> selectByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    
    /**
     * 删除过期日志
     */
    int deleteExpiredLogs(@Param("expireTime") LocalDateTime expireTime);
}
