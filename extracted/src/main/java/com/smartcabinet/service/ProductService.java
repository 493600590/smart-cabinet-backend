package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.entity.Product;

import java.util.List;

/**
 * 商品服务接口
 * 
 * @author SmartCabinet Team
 */
public interface ProductService extends IService<Product> {
    
    /**
     * 分页查询商品
     */
    Page<Product> getProductPage(int pageNum, int pageSize, Long categoryId, String status);
    
    /**
     * 获取热门商品
     */
    List<Product> getHotProducts(Integer limit);
    
    /**
     * 减少商品库存
     */
    boolean decreaseStock(Long productId, Integer quantity);
    
    /**
     * 增加商品库存
     */
    boolean increaseStock(Long productId, Integer quantity);
    
    /**
     * 获取低库存商品
     */
    List<Product> getLowStockProducts(Integer threshold);
    
    /**
     * 创建商品
     */
    Product createProduct(Product product);
    
    /**
     * 更新商品信息
     */
    boolean updateProduct(Product product);
    
    /**
     * 删除商品
     */
    boolean deleteProduct(Long productId);
    
    /**
     * 上架商品
     */
    boolean onShelf(Long productId);
    
    /**
     * 下架商品
     */
    boolean offShelf(Long productId);
}
