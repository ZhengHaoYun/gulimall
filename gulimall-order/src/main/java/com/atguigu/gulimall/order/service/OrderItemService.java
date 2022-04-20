package com.atguigu.gulimall.order.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 订单项信息
 *
 * @author zhenghaoyun
 * @email zheng.haoyun@qq.com
 * @date 2022-04-21 00:25:37
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

