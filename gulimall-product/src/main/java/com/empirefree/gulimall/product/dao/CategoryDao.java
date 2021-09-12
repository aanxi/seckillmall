package com.empirefree.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.empirefree.gulimall.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}
