package com.sb.quartz.job;

import com.sb.quartz.service.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @description：初始化任务
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.job、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午1:34
 * @mofified By:
 */
public class JobInit implements Job {

    @Autowired
    protected JobService jobService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jobService.testTask();
        jobService.testTask2();
    }
}
