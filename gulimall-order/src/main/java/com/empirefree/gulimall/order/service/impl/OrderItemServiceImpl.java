package com.empirefree.gulimall.order.service.impl;

import com.empirefree.gulimall.order.entity.OrderEntity;
import com.empirefree.gulimall.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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

@RabbitListener(queues = {"GuliMall-java-queue"})   //底层@Target表明可以修饰方法与类
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
    /**
     * Author: HuYuQiao
     * Description:
     * 1.同一条消息，只有一个消息队列会接受处理
     * 2.每条消息的接受都到等待队列处理完上一条消息
     * 3.区分消息（消息发送者类型不同）
     * @RabbitListener(标注与类和方法上)
     * @RabbitHandler(标注与方法上，用重载不同消息处理实体类进行区分消息如何处理。)
     *
     */
    @RabbitHandler
    public void receiveMessage(Message message, OrderReturnReasonEntity content, Channel channel) throws InterruptedException {
        // 1、收发消息
        System.out.println("开始接受消息---");
        byte[] body = message.getBody();
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("OrderItemServiceImpl.receiveMessage--接受到了消息:" + message
                + " | " + message.getClass()
                + " | " + content);
        System.out.println("消息处理完成---");

        // 2、手动确认消息--idea-stop依然会执行接下来的函数，可以直接关闭idea即可实现消息从unack->ready模式切换
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 2.1、确认消息
            channel.basicAck(deliverTag, false);

            // 2.2、消息id、是否批量处理（true则之前所有消息全被拒）、是否重新入队（被拒绝的消息是否重新入队）
            //channel.basicNack(deliverTag, false, false);;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RabbitHandler
    public void receiveMessage(OrderEntity content, Channel channel) throws InterruptedException {
        System.out.println("OrderItemServiceImpl.receiveMessage--接受到了消息:" + content);
    }

}