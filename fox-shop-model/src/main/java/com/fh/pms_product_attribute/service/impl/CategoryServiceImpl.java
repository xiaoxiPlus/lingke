package com.fh.pms_product_attribute.service.impl;

import com.fh.pms_product_attribute.entity.Category;
import com.fh.pms_product_attribute.mapper.CategoryMapper;
import com.fh.pms_product_attribute.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
