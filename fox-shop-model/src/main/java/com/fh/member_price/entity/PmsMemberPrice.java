package com.fh.member_price.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品会员价格表
 * </p>
 *
 * @author liqiang
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsMemberPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private Long memberLevelId;

    /**
     * 会员价格
     */
    private BigDecimal memberPrice;

    private String memberLevelName;


}
