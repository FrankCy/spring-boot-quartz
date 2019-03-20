package com.sb.quartz.vo;

import java.io.Serializable;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.vo、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午3:57
 * @mofified By:
 */
public class JobDetailVO extends JobVO implements Serializable {

    private static final long serialVersionUID = 2165576102282878019L;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 上次执行时间
     */
    private String previousTime;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(String previousTime) {
        this.previousTime = previousTime;
    }

    @Override
    public String toString() {
        return "JobDetailVO{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", previousTime='" + previousTime + '\'' +
                '}';
    }
}
