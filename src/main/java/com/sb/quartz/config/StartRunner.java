package com.sb.quartz.config;

import com.sb.quartz.service.JobService;
import com.sb.quartz.util.Constants;
import com.sb.quartz.util.DateUtil;
import com.sb.quartz.vo.JobInitVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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

    @Autowired
    QuartzManager quartzManager;

    @Autowired
    JobService jobService;

    @Override
    public void run(String... args) {
        logger.info("查询任务是否创建");
        if(!jobService.selectJobMain(Constants.JOB_INIT_INIT_JOB)) {
            Timestamp timestamp = DateUtil.getTime();
            logger.info("创建initJob begin");
            JobInitVO jobMain = new JobInitVO();
            jobMain.setJobName(Constants.JOB_INIT_INIT_JOB);
            jobMain.setJobGroup(Constants.JOB_DEFAULT_GROUP_NAME);
            jobMain.setCreateTime(timestamp);
            jobMain.setCroExp(JOB_INIT_CRON_EXP);
            jobMain.setIsUse("0");
            jobMain.setUpdateTime(timestamp);
            jobMain.setLastExecuteTime(timestamp);
            if(jobService.insertJobMain(jobMain) > 0) {
                logger.info("创建JobMain成功");
            } else {
                logger.info("创建JobMain失败");
            }
            logger.info("创建initJob end");
        }

        logger.info("定时任务初始化 ———————— begin");
        if(quartzManager.addJob(Constants.JOB_INIT_INIT_JOB, JOB_INIT_INI_JOB, JOB_INIT_CRON_EXP)) {
            logger.info("["+ Constants.JOB_INIT_INIT_JOB + "]" + "定时任务初始化成功!");
        } else {
            logger.info("["+ Constants.JOB_INIT_INIT_JOB + "]" + "定时任务初始化失败!");
        }
        logger.info("定时任务初始化 ———————— end");
    }
}
