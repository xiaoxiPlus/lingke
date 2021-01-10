package com.fh.brand.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.OssUtils;
import com.fh.brand.entity.PmsBrand;
import com.fh.brand.service.IPmsBrandService;
import com.fh.codeGather.CommonReturn;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/brand/pms-brand")
public class PmsBrandController {
    @Autowired
    private IPmsBrandService iPmsBrandService;

    @GetMapping("/queryBrandOptions")
    @PreAuthorize("hasAuthority('pms:band')")
    public CommonReturn queryBrandOptions() {
        List<PmsBrand> list = iPmsBrandService.list();
        List<Map<String,Object>> listMap = new ArrayList<>();
        list.forEach(pmsBrand->{
        Map<String,Object> map=new HashMap<>();
            map.put("value",pmsBrand.getId());
            map.put("label",pmsBrand.getName());
            listMap.add(map);
        });
        return CommonReturn.success(listMap);
    }

    @GetMapping("/queryBrandData")
    public CommonReturn queryBrandData(Page<PmsBrand> page,PmsBrand brand) {
        QueryWrapper<PmsBrand> wrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(brand.getName())){
            wrapper.like("name","%"+brand.getName()+"%");
        }
        IPage<PmsBrand> ipage = iPmsBrandService.page(page,wrapper);
        return CommonReturn.success(ipage);
    }

    @PostMapping("/upload")
    @ApiOperation("文件上传oss")
    public CommonReturn uploadPhoto(@RequestParam("file") @ApiParam(value = "上传的文件", required = true, type = "file") MultipartFile file) throws IOException {
        String photoUrl = OssUtils.uploadPhoto("productIcon", file);
        return CommonReturn.success(photoUrl);
    }

    @PostMapping("/saveOrUpdateBrand")
    public CommonReturn saveOrUpdateBrand(PmsBrand pmsBrand){
        pmsBrand.setProductCount(0);
        pmsBrand.setProductCommentCount(0);
        iPmsBrandService.saveOrUpdate(pmsBrand);
        return CommonReturn.success();
    }
    @GetMapping("/queryBrandById")
    public CommonReturn queryBrandById(Integer id){
        PmsBrand brand = iPmsBrandService.getById(id);
        return CommonReturn.success(brand);
    }

    @DeleteMapping("/deleteBrand")
    public CommonReturn deleteBrand(Integer id){
        iPmsBrandService.removeById(id);
        return CommonReturn.success();
    }

    @PutMapping("/updateFactoryStatus")
    public CommonReturn updateFactoryStatus(PmsBrand pmsBrand){
        iPmsBrandService.updateById(pmsBrand);
        return CommonReturn.success();
    }

}
