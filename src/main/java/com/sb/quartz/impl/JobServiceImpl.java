package com.sb.quartz.impl;

import com.sb.quartz.mapper.JobDetailMapper;
import com.sb.quartz.mapper.JobMainMapper;
import com.sb.quartz.po.JobDetail;
import com.sb.quartz.po.JobMain;
import com.sb.quartz.service.JobService;
import com.sb.quartz.util.Constants;
import com.sb.quartz.util.DateUtil;
import com.sb.quartz.vo.JobInitVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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
