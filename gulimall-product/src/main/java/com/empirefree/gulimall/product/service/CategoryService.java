package com.empirefree.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-08 09:35:34
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuIds(List<Long> asList);
}

