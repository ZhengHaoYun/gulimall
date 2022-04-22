package com.atguigu.gulimall.member.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.MemberReceiveAddressEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author zhenghaoyun

 * @date 2022-04-21 00:21:08
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

