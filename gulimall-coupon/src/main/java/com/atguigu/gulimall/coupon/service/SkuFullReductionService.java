package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SkuFullReductionEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 商品满减信息
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:14:03
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

