package com.fh.product.entity.vo;

import com.fh.member_price.entity.PmsMemberPrice;
import com.fh.product.entity.PmsProduct;
import com.fh.productAttributeValue.entity.PmsProductAttributeValue;
import com.fh.productFullReduction.entity.PmsProductFullReduction;
import com.fh.productLader.entity.PmsProductLadder;
import com.fh.stuTock.entity.PmsSkuStock;
import lombok.Data;

import java.util.List;

@Data
public class PmsProductVO extends PmsProduct {

    /*sku数据 库存 价格 图片*/
    private List<PmsSkuStock> skuStockList;
    /*会员价格*/
    private List<PmsMemberPrice> memberPriceList;
    /*阶梯价格*/
    private List<PmsProductLadder> productLadderList;
    /*属性  参数*/
    private List<PmsProductAttributeValue> productAttributeValueList;
    /*价格满减*/
    private List<PmsProductFullReduction> productFullReductionList;

}
