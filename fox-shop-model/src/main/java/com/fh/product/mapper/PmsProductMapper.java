package com.fh.product.mapper;

import com.fh.product.entity.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author liqiang
 * @since 2020-12-13
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
    List<Map<String,Object>> productCount();

}
