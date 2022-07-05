package com.atguigu.common.to;

import lombok.Data;

/**
 * 描述: SkuHasStockVo
 *
 * @author haoyunzheng
 * @date 2022-07-05 11:46
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
