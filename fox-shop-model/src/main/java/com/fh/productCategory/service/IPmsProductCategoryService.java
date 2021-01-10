package com.fh.productCategory.service;

import com.fh.productCategory.entity.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-09
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {


    List<Map<String,Object>> queryProductOptions();

    List<Map<String,Object>> queryCategoryOptions();
}
