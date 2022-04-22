package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.SeckillPromotionEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 秒杀活动
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:14:04
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

