package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.GulimallProductApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * 描述: Redis Test
 *
 * @author haoyunzheng
 * @date 2022-07-20 17:36
 */
@SpringBootTest(classes = GulimallProductApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("test", UUID.randomUUID().toString());

        System.out.println(ops.get("test"));
    }

    @Test
    public void redission(){
        System.out.println(redissonClient);
    }
}
