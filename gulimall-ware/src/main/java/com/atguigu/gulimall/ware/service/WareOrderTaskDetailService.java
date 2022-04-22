package com.atguigu.gulimall.ware.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.WareOrderTaskDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 库存工作单
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:28:06
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

