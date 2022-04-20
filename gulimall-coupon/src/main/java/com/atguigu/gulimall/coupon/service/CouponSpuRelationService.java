package com.atguigu.gulimall.coupon.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.coupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 优惠券与产品关联
 *
 * @author zhenghaoyun
 * @email zheng.haoyun@qq.com
 * @date 2022-04-21 00:14:04
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

