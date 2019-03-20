package com.sb.quartz.controller;

import com.sb.quartz.config.QuartzManager;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.controller、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午1:08
 * @mofified By:
 */
public class IndexController {
    public static void main(String[] args) {
        QuartzManager quartzManager = new QuartzManager();
        quartzManager.addJob("frank_job", "com.sb.quartz.job.JobConfig", "*/5 * * * * ?");
    }
}
