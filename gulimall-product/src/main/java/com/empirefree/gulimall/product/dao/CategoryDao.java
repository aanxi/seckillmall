package com.empirefree.gulimall.product.dao;

import com.empirefree.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-08 09:35:34
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
