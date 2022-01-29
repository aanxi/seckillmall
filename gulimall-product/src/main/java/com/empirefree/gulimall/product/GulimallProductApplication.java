package com.empirefree.gulimall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient是服务注册功能
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallProductApplication {


    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
