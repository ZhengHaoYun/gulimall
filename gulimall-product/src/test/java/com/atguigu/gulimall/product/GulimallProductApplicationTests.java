package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        // BrandEntity brandEntity = new BrandEntity();
        // brandEntity.setDescript("hello");
        // brandEntity.setName("华为");
        // brandService.save(brandEntity);
        // System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(
                new QueryWrapper<BrandEntity>().lambda().eq(BrandEntity::getBrandId, 1L));
        list.forEach(System.out::println);
    }
}