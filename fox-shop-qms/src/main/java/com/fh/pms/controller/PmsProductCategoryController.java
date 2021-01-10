package com.fh.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.OssUtils;
import com.fh.codeGather.CommonReturn;
import com.fh.productCategory.entity.PmsProductCategory;
import com.fh.productCategory.entity.vo.PmsProductCategoryVo;
import com.fh.productCategory.service.IPmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/productCategory/pms-product-category")
@Api(value = "PmsProductCategoryController", description = "商品分类控制层")
public class PmsProductCategoryController {

    @Autowired
    private IPmsProductCategoryService categoryService;

    @GetMapping
    @ApiOperation("分页查询总数据")
    public CommonReturn queryProduct(@ApiParam(value = "分页数据", required = true, type = "Object") Page<PmsProductCategory> page, PmsProductCategoryVo pmsProductCategoryVo) {

        QueryWrapper<PmsProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("level", pmsProductCategoryVo.getLevel());
        if (pmsProductCategoryVo.getId() != null) {
            wrapper.eq("parent_id", pmsProductCategoryVo.getId());
        }
        if (!StringUtils.isBlank(pmsProductCategoryVo.getName())){
            wrapper.like("name","%"+pmsProductCategoryVo.getName()+"%");
        }
        IPage<PmsProductCategory> ipage = categoryService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PutMapping("/navStatus")
    @ApiOperation("修改是否显示导航栏")
    public CommonReturn updateProNavStatus(@ApiParam(value = "用户", required = true, type = "Object") PmsProductCategory pmsProductCategory) {
        categoryService.updateById(pmsProductCategory);
        return CommonReturn.success();
    }

    @PutMapping("/status")
    @ApiOperation("修改显示状态")
    public CommonReturn updateProStatus(@ApiParam(value = "用户", required = true, type = "Object") PmsProductCategory pmsProductCategory) {
        categoryService.updateById(pmsProductCategory);
        return CommonReturn.success();
    }

    @PostMapping("/addProduct")
    @ApiOperation("增加数据")
    public CommonReturn addProduct(@ApiParam(value = "用户", required = true, type = "Object") PmsProductCategory pmsProductCategory) {
        pmsProductCategory.setProductCount(0);
        categoryService.save(pmsProductCategory);
        return CommonReturn.success();
    }

    @GetMapping("/queryProductOptions")
    @ApiOperation("查询下拉框的数据")
    public CommonReturn queryProductOptions() {
       List<Map<String,Object>> list = categoryService.queryProductOptions();
        return CommonReturn.success(list);
    }

    @PostMapping("/upload")
    @ApiOperation("文件上传oss")
    public CommonReturn  uploadPhoto(@RequestParam("file") @ApiParam(value = "上传的文件",required = true,type = "file") MultipartFile file) throws IOException {
        String photoUrl = OssUtils.uploadPhoto("productIcon", file);
        return CommonReturn.success(photoUrl);
    }

    @GetMapping("/queryProduct")
    @ApiOperation("回显单条数据")
    public CommonReturn queryProduct(@ApiParam(value = "用户id",required = true,type = "Integer")  Integer id){
        QueryWrapper<PmsProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        PmsProductCategory category = categoryService.getOne(wrapper);
        return CommonReturn.success(category);
    }

    @PutMapping("/editProduct")
    @ApiOperation("修改")
    public CommonReturn editProduct(@ApiParam(value = "用户要修改的数据",required = true,type = "Obejct")  PmsProductCategory pmsProductCategory){
        categoryService.updateById(pmsProductCategory);
        return CommonReturn.success();
    }

    @GetMapping("/queryCategoryOptions")
    public CommonReturn queryCategoryOptions(){
      List<Map<String,Object>> list= categoryService.queryCategoryOptions();
        return CommonReturn.success(list);
    }

}
