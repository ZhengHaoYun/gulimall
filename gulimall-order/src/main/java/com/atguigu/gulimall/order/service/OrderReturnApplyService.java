package com.atguigu.gulimall.order.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.order.entity.OrderReturnApplyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 订单退货申请
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:25:37
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

