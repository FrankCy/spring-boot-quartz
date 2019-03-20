package com.sb.quartz.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.vo、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午6:06
 * @mofified By:
 */
public class JobInitVO extends JobDetailVO implements Serializable {

    private static final long serialVersionUID = 1592429702152160L;

    private String croExp;

    private String isUse;

    private Date createTime;

    private Date updateTime;

    private Date lastExecuteTime;

    public String getCroExp() {
        return croExp;
    }

    public void setCroExp(String croExp) {
        this.croExp = croExp;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public JobInitVO() {
    }

    public JobInitVO(String croExp, String isUse, Date createTime, Date updateTime, Date lastExecuteTime) {
        this.croExp = croExp;
        this.isUse = isUse;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lastExecuteTime = lastExecuteTime;
    }

    @Override
    public String toString() {
        return "JobInitVO{" +
                "croExp='" + croExp + '\'' +
                ", isUse='" + isUse + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastExecuteTime=" + lastExecuteTime +
                '}';
    }
}
