package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author zhenghaoyun

 * @date 2022-04-20 23:49:53
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

