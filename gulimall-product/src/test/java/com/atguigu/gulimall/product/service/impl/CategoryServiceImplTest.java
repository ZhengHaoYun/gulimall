package com.atguigu.gulimall.product.service.impl;

import com.atguigu.gulimall.product.GulimallProductApplication;
import com.atguigu.gulimall.product.service.CategoryService;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = GulimallProductApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void findCatelogPath() {
        Long[] path = categoryService.findCatelogPath(252L);
        log.info(Arrays.toString(path));
    }
}