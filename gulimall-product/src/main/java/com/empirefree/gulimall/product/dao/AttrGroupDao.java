package com.empirefree.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.empirefree.gulimall.product.entity.AttrGroupEntity;
import com.empirefree.gulimall.product.vo.SpuItemAttrGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
