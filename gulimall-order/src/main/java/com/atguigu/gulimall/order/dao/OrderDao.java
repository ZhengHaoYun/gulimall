package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author zhenghaoyun
 * @date 2022-04-21 00:25:37
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}
