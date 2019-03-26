package com.sb.quartz.config;

import com.sb.quartz.util.Constants;
import com.sb.quartz.util.DateUtil;
import com.sb.quartz.vo.JobVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.config、
 * @email: cy880708@163.com
 * @date: 2019/3/20 上午11:59
 * @mofified By:
 */
@Component
@Scope("singleton")
public class QuartzManager implements ApplicationContextAware {

    private static final Log logger = LogFactory.getLog(QuartzManager.class);

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private ApplicationContext applicationContext;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory;

    /**
     * @description：执行所有任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:05
     * @mofified By:
     */
    public void start() {
        //启动所有任务
        try {
            this.scheduler = schedulerFactory.getScheduler();
            scheduler.setJobFactory(autowiringSpringBeanJobFactory);
            //启动所有任务,这里获取AbstractTask的所有子类
            Map<String, AbstractTask> tasks = applicationContext.getBeansOfType(AbstractTask.class);
            tasks.forEach((k, v) -> {
                String cronExpression = v.getCronExpression();
                if (cronExpression != null) {
                    addJob(k, v.getClass().getName(), cronExpression);
                }
            });
            logger.info("start jobs finished.");
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("init Scheduler failed");
        }
    }

    /**
     * @description：新增定时计划
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:04
     * @mofified By:
     */
    public boolean addJob(String jobName, String jobClass, String cronExp) {
        boolean result = false;
        if (!CronExpression.isValidExpression(cronExp)) {
            logger.error("Illegal cron expression format({})" + cronExp);
            return result;
        }
        try {
            JobDetail jobDetail = JobBuilder.newJob().withIdentity(new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME))
                    .ofType((Class<Job>) Class.forName(jobClass))
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                    .withIdentity(new TriggerKey(jobName, Constants.TRIGGER_DEFAULT_GROUP_NAME))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("QuartzManager add job failed");
        }
        return result;
    }

    /**
     * @description：新增定时计划【包含参数】
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:04
     * @mofified By:
     */
    public boolean addJob(String jobName, String jobClass, String cronExp, String jsonData) {
        boolean result = false;
        if (!CronExpression.isValidExpression(cronExp)) {
            logger.error("Illegal cron expression format({})" + cronExp);
            return result;
        }
        try {
            // 初始化定时明细
            JobDetail jobDetail = JobBuilder.newJob()
                    .withIdentity(new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME))
                    // 参数封装到'data'内
                    .usingJobData("data", jsonData)
                    .ofType((Class<Job>) Class.forName(jobClass))
                    .build();
            // 触发任务
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                    .withIdentity(new TriggerKey(jobName, Constants.TRIGGER_DEFAULT_GROUP_NAME))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("QuartzManager add job failed");
        }
        return result;
    }

    /**
     * @description：新增定时计划【包含参数】
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:04
     * @mofified By:
     * @param jobName
     * @param jobClass
     * @param starTime 开始时间 秒
     * @param jsonData 参数
     * @param tautologyNum 重试次数
     */
    public boolean addJob(String jobName, String jobClass, int starTime, String jsonData, int tautologyNum) {
        boolean result = false;
        if (starTime <= 0) {
            logger.error("时间错误" + starTime);
            return result;
        }
        try {
            // 初始化定时明细
            JobDetail jobDetail = JobBuilder.newJob()
                    .withIdentity(new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME))
                    // 参数封装到'data'内
                    .usingJobData("data", jsonData)
                    .ofType((Class<Job>) Class.forName(jobClass))
                    .build();
            // 触发任务
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    // 定义名字和组
                    .withIdentity(new TriggerKey(jobName, Constants.TRIGGER_DEFAULT_GROUP_NAME))
                    .withSchedule(
                            //定义任务调度的时间间隔和次数
                            SimpleScheduleBuilder
                                    .simpleSchedule()
                                    //定义时间间隔是多少秒
                                    .withIntervalInSeconds(starTime)
                                    //定义重复执行次数
                                    .withRepeatCount(tautologyNum)
                    )
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("QuartzManager add job failed");
        }
        return result;
    }

    /**
     * @description：修改任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:04
     * @mofified By:
     */
    public boolean updateJob(String jobName, String cronExp) {
        boolean result = false;
        if (!CronExpression.isValidExpression(cronExp)) {
            logger.error("Illegal cron expression format({})" + cronExp);
            return result;
        }
        JobKey jobKey = new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME);
        TriggerKey triggerKey = new TriggerKey(jobName, Constants.TRIGGER_DEFAULT_GROUP_NAME);
        try {
            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                Trigger newTrigger = TriggerBuilder.newTrigger()
                        .forJob(jobDetail)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                        .withIdentity(new TriggerKey(jobName, Constants.TRIGGER_DEFAULT_GROUP_NAME))
                        .build();
                scheduler.rescheduleJob(triggerKey, newTrigger);
                result = true;
            } else {
                logger.error("update job name:{},group name:{} or trigger name:{},group name:{} not exists.." +
                        jobKey.getName() + jobKey.getGroup() + triggerKey.getName() + triggerKey.getGroup());
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            logger.error("update job name:{},group name:{} failed!" + jobKey.getName() + jobKey.getGroup());
        }
        return result;
    }

    /**
     * @description：删除任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:04
     * @mofified By:
     */
    public boolean deleteJob(String jobName) {
        boolean result = false;
        JobKey jobKey = new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME);
        try {
            if (scheduler.checkExists(jobKey)) {
                result = scheduler.deleteJob(jobKey);
            } else {
                logger.error("delete job name:{},group name:{} not exists." + jobKey.getName() + jobKey.getGroup());
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            logger.error("delete job name:{},group name:{} failed!" + jobKey.getName() + jobKey.getGroup());
        }
        return result;
    }

    /**
     * @description：触发任务（立即执行）
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:02
     * @mofified By:
     */
    public boolean triggerJob(String jobName) {
        boolean result = false;
        JobKey jobKey = new JobKey(jobName, Constants.JOB_DEFAULT_GROUP_NAME);
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey);
                result = true;
            } else {
                logger.error("job not found —— " + jobName);
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            logger.error("trigger job name:{},group name:{} failed!" + jobKey.getName() + jobKey.getGroup());
        }
        return result;
    }

    /**
     * @description：查询正在执行的任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午3:21
     * @mofified By:
     */
    public List<JobVO> findJobs() {
        List<JobVO> jobVOList = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    JobVO jobVO = new JobVO();
                    jobVO.setJobName(jobKey.getName());
                    jobVO.setJobGroup(jobKey.getGroup());
                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    jobVO.setJobNextTime(DateUtil.dateToString(nextFireTime));
                    jobVOList.add(jobVO);
                    System.out.println("[jobName] : " + jobKey.getName() + " [groupName] : "
                            + jobKey.getGroup() + " - " + nextFireTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobVOList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
