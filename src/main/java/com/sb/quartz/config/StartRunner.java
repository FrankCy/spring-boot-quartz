package com.sb.quartz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.config、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午4:36
 * @mofified By:
 */
@Component
public class StartRunner implements CommandLineRunner {

    private static final Log logger = LogFactory.getLog(StartRunner.class);

    /**
     * 初始化定时任务实体
     */
    private static final String JOB_INIT_INI_JOB = "com.sb.quartz.job.JobInit";

    /**
     * 5s/次
     */
    private static final String JOB_INIT_CRON_EXP = "*/5 * * * * ?";

    /**
     * 初始化JOB关键字
     */
    private static final String JOB_INIT_INIT_JOB = "initJob";

    @Autowired
    QuartzManager quartzManager;

    @Override
    public void run(String... args) {
        logger.info("定时任务初始化 ———————— start");
        if(quartzManager.addJob(JOB_INIT_INIT_JOB, JOB_INIT_INI_JOB, JOB_INIT_CRON_EXP)) {
            logger.info("["+ JOB_INIT_INIT_JOB + "]" + "定时任务初始化成功!");
        } else {
            logger.info("["+ JOB_INIT_INIT_JOB + "]" + "定时任务初始化失败!");
        }
        logger.info("定时任务初始化 ———————— end");
    }
}
