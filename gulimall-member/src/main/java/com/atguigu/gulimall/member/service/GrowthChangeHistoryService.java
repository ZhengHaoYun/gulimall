package com.atguigu.gulimall.member.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.GrowthChangeHistoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 成长值变化历史记录
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:21:08
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

