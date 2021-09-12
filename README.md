# gullimall

### 介绍

谷粒商城

### 软件架构

本商城系统采用SpringCloud-Hoxton分布式架构，商城前端使用Thymeleaf引擎进行开发，后台使用vue2.0+ES6进行开发,  
服务中心与配置中心采用Alibaba的Nacos来代替Eureka, 并且使用了Gateway来代替Zuul网关,远程调用使用到了Feign。  
数据库使用Mybatis-Plus来进行配置。基本代码使用人人开源的逆向工程来进行开发。

### 开发环境

1. 架构环境：Spring Boot2.0 + SpringCloud-Hoxton+GateWay网关 + Nacos配置中心与服务中心 +  
   Fegin + Java14 + Lombok + Druid数据源
2. 前端环境：Thymeleaf + Vue2.0 + ES6
3. 开发规范：全局跨域处理（GateWay网关处理）+ 全局统一返回 + 全局异常处理
4. 开发软件：IDEA + VScode

### 运行效果

前端界面：  
<img src="https://images.gitee.com/uploads/images/2020/0512/222148_e0df4f15_5016493.png" width="80%" height="80%" />   
后台界面：  
<img src="https://images.gitee.com/uploads/images/2020/0512/222147_faa074a2_5016493.png" width="80%" height="80%" />
<img src="https://images.gitee.com/uploads/images/2020/0512/222147_f4788930_5016493.png" width="80%" height="80%" />   
Nacos配置：  
<img src="https://images.gitee.com/uploads/images/2020/0512/222147_4fe85ddc_5016493.png" width="80%" height="80%" />

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)