package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 *
 * @author zhenghaoyun

 * @date 2022-04-20 23:49:53
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}
