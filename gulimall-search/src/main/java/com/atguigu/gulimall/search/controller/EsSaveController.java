package com.atguigu.gulimall.search.controller;

import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 描述: 保存
 *
 * @author haoyunzheng
 * @date 2022-07-05 14:54
 */
@RequestMapping("/search")
@RestController
@Slf4j
public class EsSaveController {

    @Autowired
    ProductSaveService productSaveService;

    /**
     * 保存商品
     */
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean status;
        try {
            status = productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            log.error("ES商品上架错误", e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
        return status ? R.ok() : R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
    }
}
