package com.sb.quartz.job;

import com.alibaba.fastjson.JSONObject;
import com.sb.quartz.service.ParamJobService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.job、
 * @email: cy880708@163.com
 * @date: 2019/3/26 下午1:16
 * @mofified By:
 */
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
