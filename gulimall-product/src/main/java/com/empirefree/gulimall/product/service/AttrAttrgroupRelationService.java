package com.empirefree.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-08 09:35:34
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

