package com.empirefree.gulimall.coupon.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.common.utils.Query;

import com.empirefree.gulimall.coupon.dao.CouponDao;
import com.empirefree.gulimall.coupon.entity.CouponEntity;
import com.empirefree.gulimall.coupon.service.CouponService;


@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponEntity> page = this.page(
                new Query<CouponEntity>().getPage(params),
                new QueryWrapper<CouponEntity>()
        );

        return new PageUtils(page);

    }

    @Override
    public void insert(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("胡宇乔测试券");
        this.baseMapper.insert(couponEntity);
    }

}