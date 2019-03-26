package com.sb.quartz.controller;

import com.alibaba.fastjson.JSONObject;
import com.sb.quartz.config.QuartzManager;
import com.sb.quartz.service.ParamJobService;
import com.sb.quartz.util.Constants;
import com.sb.quartz.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.controller、
 * @email: cy880708@163.com
 * @date: 2019/3/26 下午1:10
 * @mofified By:
 */
@RestController
public class ParamQuartzController {

    @Autowired
    QuartzManager quartzManager;

    @Autowired
    ParamJobService paramJobServiceImpl;

    /**
     * @description：设置投资解锁12分钟之后
     * @version 1.0
     * @author: Yang.Chang
     * @email: cy880708@163.com
     * @date: 2019/3/20 下午2:25
     * @mofified By:
     */
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

}
