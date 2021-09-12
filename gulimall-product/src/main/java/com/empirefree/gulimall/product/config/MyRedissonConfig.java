package com.empirefree.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @program: renren-fast
 * @description:
 * @author: huyuqiao
 * @create: 2021/09/05 11:28
 */
@Configuration
public class MyRedissonConfig {


    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
      /*  config.useClusterServers()
                .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");*/
        config.useSingleServer().setAddress("redis://82.156.202.23:6379");
        return Redisson.create(config);
    }
}
