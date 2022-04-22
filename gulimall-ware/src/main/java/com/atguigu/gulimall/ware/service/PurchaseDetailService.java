package com.atguigu.gulimall.ware.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * @author zhenghaoyun

 * @date 2022-04-21 00:28:06
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

