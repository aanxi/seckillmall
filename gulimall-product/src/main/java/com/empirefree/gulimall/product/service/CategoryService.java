package com.empirefree.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.product.entity.CategoryEntity;
import com.empirefree.gulimall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    /**
     * 找到catalogId 完整路径
     */
    Long[] findCateLogPath(Long catelogId);

    /**
     * 级联更新所有数据
     */
    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catelog2Vo>> getCatelogJson();

    Map<String, List<Catelog2Vo>> getCatelogJson2();

    Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithRedisLock();

    Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithLocalLock();
}

