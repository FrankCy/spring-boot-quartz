package com.sb.quartz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.config、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午12:02
 * @mofified By:
 */
@Component("testTask")
public class TestTask extends AbstractTask {

    private static final Log logger = LogFactory.getLog(TestTask.class);


    @PostConstruct
    public void init() {
        this.cronExpression = "0/2 * * * * ? ";
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("test task start execute.");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            logger.info("test task execute interrupted.");
        }
        logger.info("test task execute end.");
    }

}
