package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 *
 * @author zhenghaoyun
 * @date 2022-04-21 00:25:37
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {

}
