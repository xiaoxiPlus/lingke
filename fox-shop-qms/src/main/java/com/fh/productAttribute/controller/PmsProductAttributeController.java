package com.fh.productAttribute.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.OssUtils;
import com.fh.codeGather.CommonReturn;
import com.fh.pms_product_attribute.entity.PmsProductAttribute;
import com.fh.pms_product_attribute.entity.vo.PmsProductAttributeVo;
import com.fh.pms_product_attribute.service.IPmsProductAttributeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/productAttribute/pms-product-attribute")
public class PmsProductAttributeController {

    @Autowired
    private IPmsProductAttributeService attributeService;

    /*type=0 查询属性列表*/
    @GetMapping
    public CommonReturn queryProductAttribute(Page<PmsProductAttribute> page, PmsProductAttributeVo pmsProductAttributeVo) {
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        if (pmsProductAttributeVo.getName() != null) {
            wrapper.like("name", "%" + pmsProductAttributeVo.getName() + "%");
        }
        wrapper.eq("product_attribute_category_id", pmsProductAttributeVo.getProductAttributeCategoryId());
        wrapper.eq("type", 0);
        Page<PmsProductAttribute> list = attributeService.page(page, wrapper);
        return CommonReturn.success(list);

    }

    @PostMapping
    public CommonReturn editAttributeData(PmsProductAttribute pmsProductAttribute) {
        attributeService.saveOrUpdate(pmsProductAttribute);
        return CommonReturn.success();
    }

    @DeleteMapping
    public CommonReturn deleteAttribute(Integer id) {
        attributeService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("/{type}")
    public CommonReturn queryAttributeOptions(@PathVariable("type") Integer type) {
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 0);
        wrapper.eq("product_attribute_category_id", type);
        wrapper.orderByAsc("sort");
        List<PmsProductAttribute> list = attributeService.list(wrapper);
        return CommonReturn.success(list);
    }

    @GetMapping("/queryParamsOptions/{ty}")
    public CommonReturn queryParamsOptions(@PathVariable("ty") Integer ty) {
        QueryWrapper<PmsProductAttribute> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 1);
        wrapper.eq("product_attribute_category_id", ty);
        wrapper.orderByAsc("sort");
        List<PmsProductAttribute> list = attributeService.list(wrapper);
        return CommonReturn.success(list);
    }

    @PostMapping("/upload")
    @ApiOperation("文件上传oss")
    public CommonReturn uploadPhoto(@RequestParam("file") @ApiParam(value = "上传的文件", required = true, type = "file") MultipartFile file) throws IOException {
        String photoUrl = OssUtils.uploadPhoto("productIcon", file);
        return CommonReturn.success(photoUrl);
    }

}
