package com.empirefree.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 11:48:40
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

