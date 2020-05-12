package com.empirefree.gulimall.product.dao;

import com.empirefree.gulimall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-08 09:35:34
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
