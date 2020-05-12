package com.empirefree.gulimall.order.dao;

import com.empirefree.gulimall.order.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 12:15:24
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
