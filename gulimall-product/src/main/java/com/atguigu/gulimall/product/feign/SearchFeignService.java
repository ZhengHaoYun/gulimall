package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author haoyunzheng
 * @date 2022-07-05 15:15
 */
@FeignClient("gulimall-search")
public interface SearchFeignService {
    @PostMapping("/search/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
