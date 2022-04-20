package com.atguigu.gulimall.ware.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.WareInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author zhenghaoyun
 * @email zheng.haoyun@qq.com
 * @date 2022-04-21 00:28:06
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

