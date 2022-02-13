package com.empirefree.gulimall.order.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2022/02/13 14:32
 */
@Configuration
public class RabbitmqJsonConfig {
    /**
     * Author: HuYuQiao
     * Description: RabbitMQ--将传输的对象以JSON格式传输
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
