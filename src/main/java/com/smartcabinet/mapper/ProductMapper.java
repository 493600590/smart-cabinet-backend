package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    
    /**
     * 分页查询商品列表
     */
    Page<Product> selectProductPage(Page<Product> page, @Param("categoryId") Long categoryId, @Param("status") String status);
    
    /**
     * 查询热门商品
     */
    List<Product> selectHotProducts(@Param("limit") Integer limit);
    
    /**
     * 减少商品库存
     */
    int decreaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity, @Param("version") Integer version);
    
    /**
     * 增加商品库存
     */
    int increaseStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
    
    /**
     * 查询低库存商品
     */
    List<Product> selectLowStockProducts(@Param("threshold") Integer threshold);
}
