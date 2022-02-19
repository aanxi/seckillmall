package com.empirefree.gulimall.order.controller;

import com.empirefree.gulimall.order.entity.OrderEntity;
import com.empirefree.gulimall.order.entity.OrderReturnReasonEntity;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2022/02/19 14:32
 */

@RestController
public class RabbitmqController {

    @Autowired
    RabbitTemplate rabbitTemplate;  //用于发送消息

    @RequestMapping("SendMsg")
    public void sendMessageTest(){
        OrderReturnReasonEntity orderReturnReasonEntity = new OrderReturnReasonEntity();
        orderReturnReasonEntity.setCreateTime(new Date());
        orderReturnReasonEntity.setName("胡宇乔");
        String msg = "Hello world";
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0){
                rabbitTemplate.convertAndSend("GuliMall-java-exchange", "huyuqiao", orderReturnReasonEntity, new CorrelationData(UUID.randomUUID().toString()));
            } else {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(UUID.randomUUID().toString().replace("-", ""));
                rabbitTemplate.convertAndSend("GuliMall-java-exchange", "huyuqiao22", orderEntity,  new CorrelationData(UUID.randomUUID().toString()));
            }
        }
    }
}
