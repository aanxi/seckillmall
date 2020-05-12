package com.empirefree.gulimall.order.dao;

import com.empirefree.gulimall.order.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 12:15:25
 */
@Mapper
public interface OrderOperateHistoryDao extends BaseMapper<OrderOperateHistoryEntity> {
	
}
