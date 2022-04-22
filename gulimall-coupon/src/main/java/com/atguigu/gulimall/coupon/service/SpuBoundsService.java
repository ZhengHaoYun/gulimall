package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SpuBoundsEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:14:03
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

