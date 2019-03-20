package com.sb.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: PACKAGE_NAME、
 * @email: cy880708@163.com
 * @date: 2019/3/20 上午11:35
 * @mofified By:
 */
@SpringBootApplication
@MapperScan("com.sb.quartz.mapper")
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
