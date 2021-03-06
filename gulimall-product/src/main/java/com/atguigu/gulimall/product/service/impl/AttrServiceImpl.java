package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.ProductConstant;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVO attr) {
        AttrEntity attrEntity = new AttrEntity();
//        attrEntity.setAttrName(attr.getAttrName());
        BeanUtils.copyProperties(attr, attrEntity);
        //1?????????????????????
        this.save(attrEntity);
        //2?????????????????????
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationService.save(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, String type, Long catelogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attr_type", "base".equalsIgnoreCase(type)
                ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) {
            queryWrapper.lambda().eq(AttrEntity::getCatelogId, catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> wrapper.eq("attr_id", key).or().like("attr_name", key));
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        List<AttrRespVo> attrRespVoList = page.getRecords().stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            // ??????????????????????????????
            if ("base".equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (relation != null && relation.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroup = attrGroupService.getById(relation.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroup.getAttrGroupName());
                }
            }

            CategoryEntity category = categoryService.getById(attrEntity.getCatelogId());
            if (category != null) {
                attrRespVo.setCatelogName(category.getName());
            }

            return attrRespVo;
        }).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVoList);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attr = getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attr, attrRespVo);

        //??????????????????????????????????????????
        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

        // ??????????????????
        if (relation != null) {
            Long attrGroupId = relation.getAttrGroupId();
            attrRespVo.setAttrGroupId(attrGroupId);

            AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
            if (attrGroup != null) {
                attrRespVo.setGroupName(attrGroup.getAttrGroupName());
            }
        }

        // ??????????????????
        CategoryEntity category = categoryService.getById(attr.getCatelogId());
        if (category != null) {
            attrRespVo.setCatelogName(category.getName());
            Long[] catelogPath = categoryService.findCatelogPath(category.getCatId());
            attrRespVo.setCatelogPath(catelogPath);
        }

        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVO attrVO) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVO, attrEntity);
        this.updateById(attrEntity);

        // ??????????????????
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrVO.getAttrGroupId());
        relationEntity.setAttrId(attrVO.getAttrId());
        attrAttrgroupRelationService.saveOrUpdate(relationEntity,
                new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVO.getAttrId()));

    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(
                Collectors.toList());
        if (!attrIds.isEmpty()) {
            return this.listByIds(attrIds);
        }
        return new ArrayList<>();
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1?????????????????????????????????????????????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2????????????????????????????????????????????????????????????
        //2.1)?????????????????????????????????
        List<AttrGroupEntity> group = attrGroupService.list(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        //2.2)??????????????????????????????
        List<AttrAttrgroupRelationEntity> groupId = relationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        //2.3)?????????????????????????????????????????????????????????
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds.size() > 0) {
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> w.eq("attr_id", key).or().like("attr_name", key));
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {
        return baseMapper.selectSearchAttrs(attrIds);
    }


}