package com.empirefree.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.product.entity.SpuInfoEntity;
import com.empirefree.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo saveVo);

    void saveBatchSpuInfo(SpuInfoEntity spuInfoEntity);

    /**
     * SPU模糊查询
     */
    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    /**
     * 返回一个SpuEntity
     */
    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

