package com.smartcabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartcabinet.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类数据访问接口
 * 
 * @author SmartCabinet Team
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    /**
     * 查询所有一级分类
     */
    List<Category> selectRootCategories();
    
    /**
     * 根据父分类ID查询子分类
     */
    List<Category> selectByParentId(@Param("parentId") Long parentId);
    
    /**
     * 查询分类树结构
     */
    List<Category> selectCategoryTree();
}
