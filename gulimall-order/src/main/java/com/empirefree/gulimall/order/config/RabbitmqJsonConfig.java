package com.empirefree.gulimall.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2022/02/13 14:32
 */
@Slf4j
@Configuration
public class RabbitmqJsonConfig {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * Author: HuYuQiao
     * Description: RabbitMQ--将传输的对象以JSON格式传输
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Author: HuYuQiao
     * Description: RabbitMQ--消息确认机制
     * 1、P-B发送给broker
     *  RabbitmqJsonConfig.initRabbitTemplate--correlationData:null, ack:true, cause:null：ack=true则说明成功投递
     *  correlationData：唯一，用于标识某条消息id
     *
     * 2、e-q交换机与队列：仅发送失败才能收到（比如routekey不对）
     *
     * 3、消费端确认
     *
     */
    @PostConstruct //类对象构造完成之后执行该方法
    public void initRabbitTemplate(){
        //1、p-b端消息确认机制
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("RabbitmqJsonConfig.confirm--correlationData:{}, ack:{}, cause:{}", correlationData, b, s);
            }
        });

        //2、交换机与队列交互--仅失败投递时打印
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.info("RabbitmqJsonConfig.returnedMessage--msg:{}, replyCode:{}, replyText:{}, exchange:{}, routeKey:{}", message, i, s, s1, s2);
            }
        });
    }
}
