[CSDN Spring Boot Quartz 实现动态创建](https://blog.csdn.net/Cy_LightBule/article/details/88697675)
#  Spring Boot Quartz 实现动态创建 #
## 需求 ##
- 应用启动时，初始化定时器
- 可查询正在运行的定时器
- 可修改正在运行的定时器
- 可触发立即执行定时器
- 将定时任务入库

## 技术简介 ##
- Mybatis 数据库操作
- Spring Boot 2.0.3 基础框架
- Quartz 操作队列

## 数据结构（直接执行即可） ##
- job_main（定时任务主表）
```sql
CREATE TABLE `job_main` (
  `JOB_ID` varchar(32) NOT NULL,
  `JOB_NAME` varchar(64) NOT NULL COMMENT '定时器名称',
  `JOB_GROUP` varchar(64) NOT NULL COMMENT '所属分组',
  `CRO_EXP` varchar(64) DEFAULT NULL COMMENT '表达式[定时器执行频次]',
  `IS_USE` varchar(1) DEFAULT NULL COMMENT '是否可用 0:是；1:否',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `LAST_EXECUTE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次执行时间',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
- job_detail（定时任务详情信息）
```sql
CREATE TABLE `job_detail` (
  `JOB_DETAIL_ID` varchar(32) NOT NULL,
  `JOB_ID` varchar(32) NOT NULL,
  `DATA` varchar(255) COMMENT '执行时数据信息',
  `EXECUTE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '执行时间',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`JOB_DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
- job_modify_info（定时任务修改信息）
```sql
CREATE TABLE `job_modify_info` (
  `JOB_MODIFY_ID` varchar(32) NOT NULL,
  `JOB_ID` varchar(32) NOT NULL,
  `DESCRIPTION` varchar(255) COMMENT '修改备注',
  `BEFORE_EXP` varchar(255) COMMENT '修改前表达式[定时器执行频次]',
  `AFTER_EXP` varchar(255) COMMENT '修改后表达式[定时器执行频次]',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`JOB_MODIFY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
## 主要源代码 ##
### 应用启动，初始化定时器并入库 ##
- 实现CommandLineRunner接口
```java
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
        logger.info("查询定时是否创建");
        if(!jobService.selectJobMain(Constants.JOB_INIT_INIT_JOB)) {
            // 创建JobMain（入库）
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
```

### 编写Controller，调用Quartz 增、删、改、查方法 ##
```java
@RestController
public class JobController {

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

    /**
     * @description：初始化所有任务
     * @mofified By:
     */
    @RequestMapping(value = "/initJob", method = RequestMethod.GET)
    public ResultUtil initJob() {
        if(quartzManager.addJob(Constants.JOB_INIT_INIT_JOB, JOB_INIT_INI_JOB, JOB_INIT_CRON_EXP)) {
            return new ResultUtil.Builder<>().success("初始化任务成功").build();
        } else {
            return new ResultUtil.Builder<>().failure("初始化任务失败").build();
        }
    }

    /**
     * @description：删除定时
     * @mofified By:
     */
    @RequestMapping(value = "/deleteJob" , method = RequestMethod.GET)
    public ResultUtil deleteJob(@RequestParam("jobName") String jobName) {
        if(quartzManager.deleteJob(jobName)) {
            return new ResultUtil.Builder<>().success("删除任务成功").build();
        } else {
            return new ResultUtil.Builder<>().failure("删除任务失败").build();
        }
    }

    /**
     * @description：修改定时任务
     * @mofified By:
     */
    @RequestMapping(value = "/updateJob", method = RequestMethod.GET)
    public ResultUtil updateJob(String jobName, String exp) {
        if(quartzManager.updateJob(jobName, exp)) {
            return new ResultUtil.Builder<>().success("修改任务成功").build();
        } else {
            return new ResultUtil.Builder<>().failure("修改任务失败").build();
        }
    }

    /**
     * @description：立即执行任务
     * @mofified By:
     */
    @RequestMapping(value = "/triggerJob", method = RequestMethod.GET)
    public ResultUtil triggerJob(String jobName) {
        if(quartzManager.triggerJob(jobName)) {
            return new ResultUtil.Builder<>().success("任务触发成功").build();
        } else {
            return new ResultUtil.Builder<>().failure("任务触发失败").build();
        }
    }

    /**
     * @description：查询正在执行的定时组
     * @mofified By:
     */
    @RequestMapping(value = "/findJobs", method = RequestMethod.GET)
    public ResultUtil findJobs() {
        List<JobVO> jobVOList = quartzManager.findJobs();
        if(jobVOList.size() > 0 ) {
            return new ResultUtil.Builder<>().success(jobVOList).build();
        } else {
            return new ResultUtil.Builder<>().failure("没有正在执行的任务").build();
        }
    }

}
```

### Quartz核心工具类[QuartzManager] ##
```java
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
     * @description：修改任务
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
```
### ServiceImpl ##
```java
@Service
public class JobServiceImpl implements JobService {

    private static final Log logger = LogFactory.getLog(JobServiceImpl.class);

    @Autowired
    private JobMainMapper jobMainMapper;

    @Autowired
    private JobDetailMapper jobDetailMapper;

    @Override
    public void testTask() {
        System.out.println(new Date() + "设置了一个定时任务1，5s/次频率");

        // 查询JobMain jobName对应的JobId
        JobMain jobMain = jobMainMapper.selectByName(Constants.JOB_INIT_INIT_JOB);
        if(jobMain == null) {
            logger.info("testTask 未找到对应JOB，程序出错!");
            return;
        }

        // 将本次执行入库
        JobDetail jobDetail = new JobDetail(UUID.randomUUID().toString().replaceAll("-", ""),
                jobMain.getJobId(), "都有啥数据？", DateUtil.getTime(), DateUtil.getTime());
        if(jobDetailMapper.insertSelective(jobDetail) > 0) {
            logger.info("定时任务 [testTask] 入库成功");
        } else {
            logger.info("定时任务 [testTask] 入库失败");
        }
    }

    @Override
    public void testTask2() {
        System.out.println(new Date() + "设置了一个定时任务2，5s/次频率");

        // 查询JobMain jobName对应的JobId
        JobMain jobMain = jobMainMapper.selectByName(Constants.JOB_INIT_INIT_JOB);
        if(jobMain == null) {
            logger.info("testTask2 未找到对应JOB，程序出错!");
            return;
        }

        // 将本次执行入库
        JobDetail jobDetail = new JobDetail(UUID.randomUUID().toString().replaceAll("-", ""),
                jobMain.getJobId(), "都有啥数据？", DateUtil.getTime(), DateUtil.getTime());
        if(jobDetailMapper.insertSelective(jobDetail) > 0) {
            logger.info("定时任务 [testTask2] 入库成功");
        } else {
            logger.info("定时任务 [testTask2] 入库失败");
        }
    }

    @Override
    public int insertJobMain(JobInitVO jobInitVO) {
        JobMain jobMain = new JobMain();
        jobMain.setJobId(UUID.randomUUID().toString().replaceAll("-", ""));
        //将VO内相同的值放到PO内
        BeanUtils.copyProperties(jobInitVO, jobMain);
        return jobMainMapper.insertSelective(jobMain);
    }

    @Override
    public boolean selectJobMain(String jobName) {
        if(jobMainMapper.selectByName(jobName) == null) {
            return false;
        } else {
            return true;
        }
    }
}
```

# Spring Boot Quartz 实战 #
## 说明 ##
- **使用Quartz实现动态传递参数并触发定时**
- **使用Quartz实现重试次数和开始时间**
## 示例：动态传参并触发 ##
### Controller ###
```java
@RequestMapping(value = "/paramQuartzTest", method = RequestMethod.POST)
    public ResultUtil investUnLock(@RequestParam("id") String id) {
        // 封装JSON，将所有传参都放入JSON，并转为String进行传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        // 调用QuartzManager进行addJob(含参数）
        if(quartzManager.addJob(Constants.JOB_PARAM, Constants.PARAM_JOB_SERVICE_TRIGGER, Constants.PARAM_JOB_CRON_EXP, jsonObject.toJSONString())) {
            return new ResultUtil.Builder<>().success("定时创建成功").build();
        } else {
            return new ResultUtil.Builder<>().failure("定时创建失败").build();
        }
    }
```
### Quartz Manager ###
```java
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
```
### implements Job ###
```java
public class ParamJob implements Job {

    private static final Log logger = LogFactory.getLog(ParamJob.class);

    @Autowired
    protected ParamJobService paramJobServiceImpl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            // 从定时中获取参数
            String jsonData = jobExecutionContext.getMergedJobDataMap().getString("data");
            logger.info("jsonData : " + jsonData);
            // 解析
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            // 调用含参数定时，并传递参数
            paramJobServiceImpl.paramQuartzDemo(jsonObject.getString("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

```
## 示例：设置重试次数和开始时间 ##
### Quartz Manager ###
**```关键代码：```**
```java

/**
 * @description：新增定时计划【包含参数，设置重试】
 * @version 1.0
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
```

# 项目相关 #
[项目地址 - GItHub spring-boot-quartz ](https://github.com/FrankCy/spring-boot-quartz)
- - -
[CSDN - Spring Boot Quartz 实现动态创建](https://blog.csdn.net/Cy_LightBule/article/details/88697675)
- - -
[CSDN - Spring Boot 实现定时任务](https://blog.csdn.net/Cy_LightBule/article/details/88670489)