package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.entity.Product;
import com.smartcabinet.mapper.ProductMapper;
import com.smartcabinet.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    
    @Override
    public Page<Product> getProductPage(int pageNum, int pageSize, Long categoryId, String status) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectProductPage(page, categoryId, status);
    }
    
    @Override
    public List<Product> getHotProducts(Integer limit) {
        return baseMapper.selectHotProducts(limit);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Long productId, Integer quantity) {
        Product product = getById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        if (product.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }
        
        int result = baseMapper.decreaseStock(productId, quantity, product.getVersion());
        if (result == 0) {
            throw new RuntimeException("库存更新失败，请重试");
        }
        
        log.info("商品库存减少成功，商品ID：{}，减少数量：{}", productId, quantity);
        return true;
    }
    
    @Override
    public boolean increaseStock(Long productId, Integer quantity) {
        int result = baseMapper.increaseStock(productId, quantity);
        if (result > 0) {
            log.info("商品库存增加成功，商品ID：{}，增加数量：{}", productId, quantity);
            return true;
        }
        return false;
    }
    
    @Override
    public List<Product> getLowStockProducts(Integer threshold) {
        return baseMapper.selectLowStockProducts(threshold);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product createProduct(Product product) {
        product.setStatus("ON_SHELF");
        product.setVersion(1);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        
        save(product);
        log.info("商品创建成功，商品名称：{}", product.getName());
        return product;
    }
    
    @Override
    public boolean updateProduct(Product product) {
        product.setUpdatedAt(LocalDateTime.now());
        return updateById(product);
    }
    
    @Override
    public boolean deleteProduct(Long productId) {
        boolean result = removeById(productId);
        if (result) {
            log.info("商品删除成功，商品ID：{}", productId);
        }
        return result;
    }
    
    @Override
    public boolean onShelf(Long productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setStatus("ON_SHELF");
        product.setUpdatedAt(LocalDateTime.now());
        return updateById(product);
    }
    
    @Override
    public boolean offShelf(Long productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setStatus("OFF_SHELF");
        product.setUpdatedAt(LocalDateTime.now());
        return updateById(product);
    }
}
