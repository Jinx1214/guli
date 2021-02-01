package com.atguigu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan("com.atguigu")
@MapperScan(basePackages = "com.atguigu.statistics.mapper")

@EnableFeignClients
public class statisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(statisticsApplication.class,args);
    }
}
