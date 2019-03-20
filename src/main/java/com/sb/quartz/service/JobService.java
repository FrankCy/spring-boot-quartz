package com.sb.quartz.service;

import com.sb.quartz.vo.JobInitVO;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.service、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午1:27
 * @mofified By:
 */
public interface JobService {

    void testTask();

    void testTask2();

    /**
     * @description：创建初始化任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午6:05
     * @mofified By:
     */
    int insertJobMain(JobInitVO jobInitVO);

    /**
     * @description：查询是否又这个任务
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午6:05
     * @mofified By:
     */
    boolean selectJobMain(String jobName);

}
