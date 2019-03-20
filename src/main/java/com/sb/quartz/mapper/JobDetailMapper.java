package com.sb.quartz.mapper;

import com.sb.quartz.po.JobDetail;

public interface JobDetailMapper {
    int deleteByPrimaryKey(String jobDetailId);

    int insert(JobDetail record);

    int insertSelective(JobDetail record);

    JobDetail selectByPrimaryKey(String jobDetailId);

    int updateByPrimaryKeySelective(JobDetail record);

    int updateByPrimaryKey(JobDetail record);
}