package com.sb.quartz.impl;

import com.sb.quartz.service.ParamJobService;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.impl、
 * @email: cy880708@163.com
 * @date: 2019/3/26 下午1:11
 * @mofified By:
 */
@Service
public class ParamJobServiceImpl implements ParamJobService {
    @Override
    public boolean paramQuartzDemo(String id) {
        System.out.println("id : " + id);
        return false;
    }
}
