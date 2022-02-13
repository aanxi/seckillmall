package com.empirefree.gulimall.order.service.impl;

import com.empirefree.gulimall.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.empirefree.common.utils.PageUtils;
import com.empirefree.common.utils.Query;

import com.empirefree.gulimall.order.dao.OrderItemDao;
import com.empirefree.gulimall.order.entity.OrderItemEntity;
import com.empirefree.gulimall.order.service.OrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    @RabbitListener(queues = {"GuliMall-java-queue"})
    public void receiveMessage(Message message, OrderReturnReasonEntity content, Channel channel){
        byte[] body = message.getBody();
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("OrderItemServiceImpl.receiveMessage--接受到了消息:" + message
                + " | " + message.getClass()
                + " | " + content);
    }

}