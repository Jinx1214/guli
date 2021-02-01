package com.atguigu.order;

import com.atguigu.Result.R;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "com.atguigu.order.mapper")
@ComponentScan("com.atguigu")
public class orderApplication {
    public static void main(String[] args) {
        SpringApplication.run(orderApplication.class, args);
    }
}
