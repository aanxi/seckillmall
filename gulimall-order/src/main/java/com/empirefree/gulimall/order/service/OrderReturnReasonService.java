package com.empirefree.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.gulimall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author Empirefree
 * @email skt.hyq@gmail.com
 * @date 2020-05-09 12:15:24
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

