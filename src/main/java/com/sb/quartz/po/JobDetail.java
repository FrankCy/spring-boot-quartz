package com.sb.quartz.po;

import java.io.Serializable;
import java.util.Date;

public class JobDetail implements Serializable {

    private static final long serialVersionUID = 3045538200287492856L;

    private String jobDetailId;

    private String jobId;

    private String data;

    private Date executeTime;

    private Date createTime;

    public String getJobDetailId() {
        return jobDetailId;
    }

    public void setJobDetailId(String jobDetailId) {
        this.jobDetailId = jobDetailId == null ? null : jobDetailId.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public JobDetail() {
    }

    public JobDetail(String jobDetailId, String jobId, String data, Date executeTime, Date createTime) {
        this.jobDetailId = jobDetailId;
        this.jobId = jobId;
        this.data = data;
        this.executeTime = executeTime;
        this.createTime = createTime;
    }
}