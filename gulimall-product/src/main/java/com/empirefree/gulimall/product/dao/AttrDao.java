package com.empirefree.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.empirefree.gulimall.product.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
