package com.sb.quartz.vo;

import java.io.Serializable;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.vo、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午3:18
 * @mofified By:
 */
public class JobVO implements Serializable {

    private static final long serialVersionUID = 6523372777099119154L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务所属组
     */
    private String jobGroup;

    /**
     * 下次执行时间
     */
    private String jobNextTime;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobNextTime() {
        return jobNextTime;
    }

    public void setJobNextTime(String jobNextTime) {
        this.jobNextTime = jobNextTime;
    }

    public JobVO() {
    }

    public JobVO(String jobName, String jobGroup, String jobNextTime) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobNextTime = jobNextTime;
    }

    @Override
    public String toString() {
        return "JobVO{" +
                "jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobNextTime='" + jobNextTime + '\'' +
                '}';
    }
}
