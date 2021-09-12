package com.empirefree.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.empirefree.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.empirefree.gulimall.product.vo.ItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-05-31 17:06:04
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<ItemSaleAttrVo> getSaleAttrsBuSpuId(@Param("spuId") Long spuId);

    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);
}
