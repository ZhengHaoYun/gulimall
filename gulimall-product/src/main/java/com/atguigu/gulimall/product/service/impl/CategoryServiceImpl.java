package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查出所有分类
        List<CategoryEntity> categories = list();

        // 2.组装成父子的树形结构
        List<CategoryEntity> treeCategory = categories.stream().filter(c -> c.getParentCid() == 0).peek(c -> {
                    List<CategoryEntity> childrenCategory = getChildren(c, categories);
                    c.setChildren(childrenCategory);
                }).sorted(Comparator.comparingInt(c -> Optional.ofNullable(c.getSort()).orElse(0)))
                .collect(Collectors.toList());
        return treeCategory;
    }

    @Override
    public Long[] findCatelogPath(Long catId) {
        List<Long> path = new ArrayList<>();
        findParentId(catId, path);
        Collections.reverse(path);

        return path.toArray(new Long[0]);
    }

    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
        this.updateById(category);
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        return selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        // 将数据库的多次查询变为一次
        List<CategoryEntity> categories = this.baseMapper.selectList(null);

        // 查出所有一级分类
        List<CategoryEntity> level1Categories = getParent_cid(categories, 0L);

        // 封装数据
        return level1Categories.stream().collect(
                Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    // 根据一级分类查出所有的二级分类
                    List<CategoryEntity> categoryEntities = getParent_cid(categories, v.getCatId());
                    // 封装结果
                    List<Catelog2Vo> res = new ArrayList<>();
                    if (categoryEntities != null) {
                        res = categoryEntities.stream().map(l2 -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null,
                                    l2.getCatId().toString(), l2.getName());
                            // 找出二级分类下的三级分类
                            List<CategoryEntity> level3Catelog = getParent_cid(categories, l2.getCatId());
                            if (level3Catelog != null) {
                                List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream()
                                        .map(l3 -> new Catelog2Vo.Category3Vo(l2.getCatId().toString(),
                                                l3.getCatId().toString(), l3.getName()))
                                        .collect(Collectors.toList());
                                catelog2Vo.setCatalog3List(category3Vos);
                            }

                            return catelog2Vo;
                        }).collect(Collectors.toList());
                    }

                    return res;
                }));
    }

    private void findParentId(Long catId, List<Long> path) {
        path.add(catId);
        CategoryEntity category = this.getById(catId);
        if (category.getParentCid() != 0) {
            findParentId(category.getParentCid(), path);
        }
    }

    /**
     * 找出所有子分类
     */
    private List<CategoryEntity> getChildren(CategoryEntity category, List<CategoryEntity> categories) {
        return categories.stream()
                .filter(c -> Objects.equals(c.getParentCid(), category.getCatId()))
                .peek(c -> c.setChildren(getChildren(c, categories)))
                .sorted(Comparator.comparingInt(c -> Optional.ofNullable(c.getSort()).orElse(0)))
                .collect(Collectors.toList());
    }

}