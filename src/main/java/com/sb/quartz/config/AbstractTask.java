package com.sb.quartz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.config、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午12:01
 * @mofified By:
 */
public abstract class AbstractTask implements Job {

    private static final Log logger = LogFactory.getLog(AbstractTask.class);

    protected abstract void executeInternal(JobExecutionContext context);

    protected String cronExpression;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            executeInternal(context);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("job execute failed!");
        }
    }

    public String getCronExpression() {
        return cronExpression;
    }

}
