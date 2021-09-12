package com.empirefree.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.ware.entity.WareInfoEntity;
import com.empirefree.gulimall.ware.vo.FareVo;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author empirefree
 * @email 1842449680@qq.com
 * @date 2020-06-06 21:09:28
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

