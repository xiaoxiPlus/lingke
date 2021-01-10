package com.fh.resource.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.umsResource.entity.UmsResource;
import com.fh.umsResource.entity.vo.UmsResourceVo;
import com.fh.umsResource.service.IUmsResourceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsResource/ums-resource")
public class UmsResourceController {

    @Autowired
    private IUmsResourceService resourceService;

    @GetMapping
    public CommonReturn queryResource(Page<UmsResource> page, UmsResourceVo resourceVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(resourceVo.getName())) {
            wrapper.like("name", resourceVo.getName());
        }
        if (!StringUtils.isBlank(resourceVo.getUrl())) {
            wrapper.like("url", resourceVo.getUrl());
        }
        if (resourceVo.getCategoryId() != null && resourceVo.getCategoryId() != 0) {
            wrapper.eq("category_id", resourceVo.getCategoryId());
        }
        if (resourceVo.getStartTime() != null) {
            String format = sdf.format(resourceVo.getStartTime());
            if (!format.equals("")) {
                wrapper.ge("create_time", format);
            }
        }
        if (resourceVo.getEndTime() != null) {
            String format = sdf.format(resourceVo.getEndTime());
            if (!format.equals("")) {
                wrapper.le("create_time", format);
            }
        }
        IPage<UmsResource> ipage = resourceService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PutMapping
    public CommonReturn updateResourceStatus(UmsResource resource) {
        resourceService.updateById(resource);
        return CommonReturn.success();
    }

    @PostMapping
    public CommonReturn saveOrUpdateResource(UmsResource resource) {
        if (resource.getId() == null) {
            resource.setCreateTime(new Date());
        }
        resourceService.saveOrUpdate(resource);
        return CommonReturn.success();
    }

    @GetMapping("/{id}")
    public CommonReturn queryResourceById(@PathVariable("id") Integer id) {
        UmsResource resource = resourceService.getById(id);
        return CommonReturn.success(resource);
    }

    @DeleteMapping
    public CommonReturn deleteResource(Integer id) {
        resourceService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("/data")
    public CommonReturn queryResourceData() {
        List<UmsResource> list = resourceService.list();
        return CommonReturn.success(list);
    }

}
