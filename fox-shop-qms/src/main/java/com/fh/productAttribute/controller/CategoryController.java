package com.fh.productAttribute.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.pms_product_attribute.entity.Category;
import com.fh.pms_product_attribute.entity.PmsProductAttribute;
import com.fh.pms_product_attribute.service.ICategoryService;
import com.fh.pms_product_attribute.service.IPmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/pms_product_attribute/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IPmsProductAttributeService attributeService;


    @GetMapping
    public CommonReturn queryProductAttribute(Page<Category> page,Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (category.getName()!=null){
            wrapper.like("name","%"+category.getName()+"%");
        }
        IPage<Category> ipage = categoryService.page(page,wrapper);
        return CommonReturn.success(ipage);
    }

    @PostMapping
    public CommonReturn saveOrUpdateAttribute(Category category){
        categoryService.saveOrUpdate(category);
        return CommonReturn.success();
    }

    @DeleteMapping
    public CommonReturn deleteAttribute(Integer id){
        categoryService.removeById(id);
        attributeService.remove(new QueryWrapper<PmsProductAttribute>()
                            .eq("product_attribute_category_id",id)

        );
        return CommonReturn.success();
    }

    @GetMapping("/queryCateOptions")
    public CommonReturn queryCateOptions(){
        List<Category> list = categoryService.list();
        return CommonReturn.success(list);
    }

}
