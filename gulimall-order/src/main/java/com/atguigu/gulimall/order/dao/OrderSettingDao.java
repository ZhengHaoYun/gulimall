package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:25:37
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {

}
