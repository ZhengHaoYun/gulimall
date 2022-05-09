package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
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