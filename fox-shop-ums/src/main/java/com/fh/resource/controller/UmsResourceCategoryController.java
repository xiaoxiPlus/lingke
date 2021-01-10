package com.fh.resource.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.umsResource.entity.UmsResource;
import com.fh.umsResource.entity.UmsResourceCategory;
import com.fh.umsResource.service.IUmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsResource/ums-resource-category")
public class UmsResourceCategoryController {

    @Autowired
    private IUmsResourceCategoryService categoryService;

    @GetMapping
    public CommonReturn queryResource() {
        List<UmsResourceCategory> list = categoryService.list();
        return CommonReturn.success(list);
    }

    @GetMapping("/data")
    public CommonReturn queryResourceCategory(Page<UmsResourceCategory> page) {
        IPage<UmsResourceCategory> ipage = categoryService.page(page);
        return CommonReturn.success(ipage);
    }

    @PostMapping
    public CommonReturn saveOrUpdateResource(UmsResourceCategory category) {
        if (category.getId() == null) {
            category.setCreateTime(new Date());
        }
        categoryService.saveOrUpdate(category);
        return CommonReturn.success();
    }

    @GetMapping("/{id}")
    public CommonReturn queryResourceById(@PathVariable("id") Integer id) {
        UmsResourceCategory category = categoryService.getById(id);
        return CommonReturn.success(category);
    }

    @DeleteMapping
    public CommonReturn deleteResource(Integer id) {
        categoryService.removeById(id);
        return CommonReturn.success();
    }



}
