package com.empirefree.gulimall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  RabbitMQ使用(SpringBoot其他功能亦是类似)--源码分析
 *  1.导入amqp包：RabbitAutoConfiguration就会自动生效
 *  2.RabbitAutoConfiguration给容器自动配置了相关注解，配置类在ConnectionFactory.RabbitProperties中
 *  3.配置文件添加相关配置信息
 *  4.@EnableRabbit:EnableXXX都是如此
 *  5.监听消息 @RabbitListener（前提是必须开启@EnableRabbit并且注入到了容器中）
 */
@EnableRabbit
@SpringBootApplication
public class GulimallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallOrderApplication.class, args);
    }

}
