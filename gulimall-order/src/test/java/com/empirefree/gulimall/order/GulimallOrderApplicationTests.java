package com.empirefree.gulimall.order;

import com.empirefree.gulimall.order.entity.OrderEntity;
import com.empirefree.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class GulimallOrderApplicationTests {


    @Autowired
    AmqpAdmin amqpAdmin;   //用于创建rabbitmq基本组件与绑定关系

    @Autowired
    RabbitTemplate rabbitTemplate;  //用于发送消息

    @Test
    public void sendMessageTest(){
        OrderReturnReasonEntity orderReturnReasonEntity = new OrderReturnReasonEntity();
        orderReturnReasonEntity.setCreateTime(new Date());
        orderReturnReasonEntity.setName("胡宇乔");
        String msg = "Hello world";
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0){
                rabbitTemplate.convertAndSend("GuliMall-java-exchange", "huyuqiao", orderReturnReasonEntity);
            } else {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(UUID.randomUUID().toString().replace("-", ""));
                rabbitTemplate.convertAndSend("GuliMall-java-exchange", "huyuqiao", orderEntity);
            }
        }
    }

    @Test
    public void createExchange(){
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        DirectExchange directExchange = new DirectExchange("GuliMall-java-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("GulimallOrderApplicationTests.createExchange--Exchange创建成功{}", directExchange);
    }
    @Test
    public void createQueue(){
        //@Nullable可以传入空值
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Queue queue = new Queue("GuliMall-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("GulimallOrderApplicationTests.createQueue--Queue创建成功{}", "GuliMall-java-queue");

    }

    @Test
    public void createBinding(){
        //String destination, Binding.DestinationType destinationType, String exchange,
        // String routingKey, @Nullable Map<String, Object> arguments
        Binding binding = new Binding("GuliMall-java-queue",
                Binding.DestinationType.QUEUE,
                "GuliMall-java-exchange",
                "huyuqiao", null
                );
        amqpAdmin.declareBinding(binding);
    }

    @Test
    void contextLoads() {
    }

}
