package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zhenghaoyun

 * @date 2022-04-20 23:49:53
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

