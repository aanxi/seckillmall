package com.empirefree.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 11:48:40
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

