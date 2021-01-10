package com.fh.product.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.product.entity.PmsProduct;
import com.fh.product.entity.vo.PmsProductVO;
import com.fh.product.service.IPmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/product/pms-product")
public class PmsProductController {

    @Autowired
    private IPmsProductService productService;

    @GetMapping
    public CommonReturn queryProductData(Page<PmsProduct> page) {
        QueryWrapper<PmsProduct> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        IPage<PmsProduct> ipage = productService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PostMapping
    public CommonReturn saveOrUpdateProduct(@RequestBody PmsProductVO pmsProductVO) {
        productService.saveOrUpdateProduct(pmsProductVO);
        return CommonReturn.success();
    }

    @GetMapping("/{productId}")
    public CommonReturn queryProductById(@PathVariable("productId") Integer productId) {
        PmsProductVO pmsProductVO = productService.queryProductById(productId);
        /*父子类之间数据转换 第一个为父类 */
        return CommonReturn.success(pmsProductVO);
    }

    @GetMapping("/productCount")
    public CommonReturn productCount() {
        List<Map<String, Object>> list = productService.productCount();
        /*父子类之间数据转换 第一个为父类 */
        return CommonReturn.success(list);
    }

}
