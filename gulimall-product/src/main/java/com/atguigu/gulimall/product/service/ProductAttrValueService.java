package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * spu属性值
 *
 * @author zhenghaoyun

 * @date 2022-04-20 23:49:53
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

