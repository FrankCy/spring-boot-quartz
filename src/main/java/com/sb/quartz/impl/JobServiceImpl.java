package com.sb.quartz.impl;

import com.sb.quartz.service.JobService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.impl、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午1:27
 * @mofified By:
 */
@Service
public class JobServiceImpl implements JobService {

    @Override
    public void testTask() {
        System.out.println(new Date() + "设置了一个定时任务1，5s/次频率");
    }

    @Override
    public void testTask2() {
        System.out.println(new Date() + "设置了一个定时任务2，5s/次频率");
    }
}
