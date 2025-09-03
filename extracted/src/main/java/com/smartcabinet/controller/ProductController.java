package com.smartcabinet.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcabinet.common.result.Result;
import com.smartcabinet.entity.Product;
import com.smartcabinet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品控制器
 * 
 * @author SmartCabinet Team
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    
    private final ProductService productService;
    
    /**
     * 分页查询商品
     */
    @GetMapping
    public Result<Page<Product>> getProducts(
            @RequestParam(defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status) {
        Page<Product> page = productService.getProductPage(pageNum, pageSize, categoryId, status);
        return Result.success(page);
    }
    
    /**
     * 获取商品详情
     */
    @GetMapping("/{id}")
    public Result<Product> getProduct(@PathVariable @NotNull Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }
    
    /**
     * 获取热门商品
     */
    @GetMapping("/hot")
    public Result<List<Product>> getHotProducts(@RequestParam(defaultValue = "10") Integer limit) {
        List<Product> products = productService.getHotProducts(limit);
        return Result.success(products);
    }
    
    /**
     * 获取低库存商品
     */
    @GetMapping("/low-stock")
    public Result<List<Product>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        return Result.success(products);
    }
    
    /**
     * 创建商品（管理员）
     */
    @PostMapping
    public Result<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return Result.success(createdProduct);
    }
    
    /**
     * 更新商品信息（管理员）
     */
    @PutMapping("/{id}")
    public Result<Boolean> updateProduct(@PathVariable @NotNull Long id,
                                        @Valid @RequestBody Product product) {
        product.setProductId(id);
        boolean success = productService.updateProduct(product);
        return Result.success(success);
    }
    
    /**
     * 删除商品（管理员）
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteProduct(@PathVariable @NotNull Long id) {
        boolean success = productService.deleteProduct(id);
        return Result.success(success);
    }
    
    /**
     * 上架商品（管理员）
     */
    @PutMapping("/{id}/on-shelf")
    public Result<Boolean> onShelf(@PathVariable @NotNull Long id) {
        boolean success = productService.onShelf(id);
        return Result.success(success);
    }
    
    /**
     * 下架商品（管理员）
     */
    @PutMapping("/{id}/off-shelf")
    public Result<Boolean> offShelf(@PathVariable @NotNull Long id) {
        boolean success = productService.offShelf(id);
        return Result.success(success);
    }
}
