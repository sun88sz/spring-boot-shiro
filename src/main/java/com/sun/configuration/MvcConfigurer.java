package com.sun.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// @EnableWebMvc
// 开启事务配置
@EnableTransactionManagement
//@EntityScan(basePackages = {"com"})
//Spring就会将指定包中@Repository的类注册为bean，将bean托管给Spring
//@EnableJpaRepositories(basePackages = { "com" })
public class MvcConfigurer {

}