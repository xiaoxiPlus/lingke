package com.fh.product.service.impl;

import com.fh.member_price.entity.PmsMemberPrice;
import com.fh.member_price.service.IPmsMemberPriceService;
import com.fh.product.entity.PmsProduct;
import com.fh.product.entity.vo.PmsProductVO;
import com.fh.product.mapper.PmsProductMapper;
import com.fh.product.service.IPmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.productAttributeValue.entity.PmsProductAttributeValue;
import com.fh.productAttributeValue.service.IPmsProductAttributeValueService;
import com.fh.productFullReduction.entity.PmsProductFullReduction;
import com.fh.productFullReduction.service.IPmsProductFullReductionService;
import com.fh.productLader.entity.PmsProductLadder;
import com.fh.productLader.service.IPmsProductLadderService;
import com.fh.stuTock.entity.PmsSkuStock;
import com.fh.stuTock.service.IPmsSkuStockService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-13
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Autowired
    private IPmsMemberPriceService memberPriceService;
    @Autowired
    private IPmsProductFullReductionService pmsProductFullReductionService;
    @Autowired
    private IPmsProductAttributeValueService attributeValueService;
    @Autowired
    private IPmsSkuStockService skuStockService;
    @Autowired
    private IPmsProductLadderService ladderService;
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Override
    /*事务*/
    @Transactional
    public void saveOrUpdateProduct(PmsProductVO pmsProductVO) {

        PmsProduct pmsProduct = pmsProductVO;
        /*save方法会自动返回一个id值*/
        boolean bool = super.saveOrUpdate(pmsProduct);
        if (bool) {
            Long productId = pmsProduct.getId();
            Map<String, Object> map = new HashMap<>();
            map.put("product_id", productId);
            /*保存会员价格*/
            saveMemberPrice(pmsProductVO.getMemberPriceList(), productId, map);
            /*保存满减价格*/
            saveFullReduction(pmsProductVO.getProductFullReductionList(), productId, map);
            /*保存手动添加的属性和参数*/
            saveAttributeValue(pmsProductVO.getProductAttributeValueList(), productId, map);
            /*保存SKU参数和属性*/
            saveSkuTock(pmsProductVO.getSkuStockList(), productId, map);
            /*保存阶梯价格*/
            saveProductLadder(pmsProductVO.getProductLadderList(),map);
        }

    }

    /*回显*/
    @Override
    public PmsProductVO queryProductById(Integer productId) {
        PmsProductVO productVO=new PmsProductVO();
        BeanUtils.copyProperties(super.getById(productId),productVO);
        Map<String,Object> map=new HashMap<>();
        map.put("product_id", productId);
        /*查询会员价格*/
        List<PmsMemberPrice> pmsMemberPrices = memberPriceService.listByMap(map);
        /*查询阶梯价格*/
        List<PmsProductLadder> pmsProductLadders = ladderService.listByMap(map);
        /*查询SKU参数*/
        List<PmsSkuStock> pmsSkuStocks = skuStockService.listByMap(map);
        /*查询参数和属性*/
        List<PmsProductAttributeValue> pmsProductAttributeValues = attributeValueService.listByMap(map);
        /*查询满减价格*/
        List<PmsProductFullReduction> pmsProductFullReductions = pmsProductFullReductionService.listByMap(map);

        productVO.setMemberPriceList(pmsMemberPrices);
        productVO.setProductLadderList(pmsProductLadders);
        productVO.setSkuStockList(pmsSkuStocks);
        productVO.setProductFullReductionList(pmsProductFullReductions);
        productVO.setProductAttributeValueList(pmsProductAttributeValues);


        return productVO;
    }



    private void saveProductLadder(List<PmsProductLadder> list, Map<String,Object> map) {
        list.forEach(productLadder->{
            productLadder.setProductId((Long) map.get("product_id"));
        });
        ladderService.removeByMap(map);
        ladderService.saveBatch(list);
    }

    private void saveSkuTock(List<PmsSkuStock> list, Long productId, Map<String, Object> map) {
        for (int i=0;i<list.size();i++){
            PmsSkuStock pmsSkuStock = list.get(i);
            pmsSkuStock.setProductId(productId);
            String dateStr = DateFormatUtils.format(new Date(),"yyyyMMdd");
            String centerStr = String.format("%06d",productId);
            String endStr = String.format("%03d",i+1);
            pmsSkuStock.setSkuCode(StringUtils.join(dateStr,centerStr,endStr));
        }

        skuStockService.removeByMap(map);
        skuStockService.saveBatch(list);
    }

    private void saveAttributeValue(List<PmsProductAttributeValue> list, Long productId, Map<String, Object> map) {
        list.forEach(pmsProductAttributeValue -> {
            pmsProductAttributeValue.setProductId(productId);
        });
        attributeValueService.removeByMap(map);
        attributeValueService.saveBatch(list);
    }

    private void saveFullReduction(List<PmsProductFullReduction> list, Long productId, Map<String, Object> map) {
        list.forEach(productFullReduction -> {
            productFullReduction.setProductId(productId);
        });
        pmsProductFullReductionService.removeByMap(map);
        pmsProductFullReductionService.saveBatch(list);
    }

    private void saveMemberPrice(List<PmsMemberPrice> list, Long productId, Map<String, Object> map) {
        list.forEach(memberPrice -> {
            memberPrice.setProductId(productId);
        });
        memberPriceService.removeByMap(map);
        memberPriceService.saveBatch(list);
    }

    public  List<Map<String, Object>> productCount(){
       return pmsProductMapper.productCount();
    }
}
