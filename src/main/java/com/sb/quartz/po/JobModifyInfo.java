package com.sb.quartz.po;

import java.util.Date;

public class JobModifyInfo {
    private String jobModifyId;

    private String jobId;

    private String description;

    private String beforeExp;

    private String afterExp;

    private Date createTime;

    public String getJobModifyId() {
        return jobModifyId;
    }

    public void setJobModifyId(String jobModifyId) {
        this.jobModifyId = jobModifyId == null ? null : jobModifyId.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBeforeExp() {
        return beforeExp;
    }

    public void setBeforeExp(String beforeExp) {
        this.beforeExp = beforeExp == null ? null : beforeExp.trim();
    }

    public String getAfterExp() {
        return afterExp;
    }

    public void setAfterExp(String afterExp) {
        this.afterExp = afterExp == null ? null : afterExp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}