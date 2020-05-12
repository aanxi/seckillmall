package com.empirefree.gulimall.member.dao;

import com.empirefree.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 11:48:40
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
