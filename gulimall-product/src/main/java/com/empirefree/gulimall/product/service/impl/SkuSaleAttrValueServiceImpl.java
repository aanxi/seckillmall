package com.empirefree.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.common.utils.Query;
import com.empirefree.gulimall.product.dao.SkuSaleAttrValueDao;
import com.empirefree.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.empirefree.gulimall.product.service.SkuSaleAttrValueService;
import com.empirefree.gulimall.product.vo.ItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ItemSaleAttrVo> getSaleAttrsBuSpuId(Long spuId) {

        SkuSaleAttrValueDao dao = this.baseMapper;
        return dao.getSaleAttrsBuSpuId(spuId);
    }

    @Override
    public List<String> getSkuSaleAttrValuesAsStringList(Long skuId) {

        SkuSaleAttrValueDao dao = this.baseMapper;
        return dao.getSkuSaleAttrValuesAsStringList(skuId);
    }
}