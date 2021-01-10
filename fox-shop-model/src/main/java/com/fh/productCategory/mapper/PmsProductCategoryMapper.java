package com.fh.productCategory.mapper;

import com.fh.productCategory.entity.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author liqiang
 * @since 2020-12-09
 */
@Mapper
@Repository
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<Map<String,Object>> queryProductOptions();
}
