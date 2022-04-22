package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:14:04
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

