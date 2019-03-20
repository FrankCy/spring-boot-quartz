package com.sb.quartz.mapper;

import com.sb.quartz.po.JobModifyInfo;

public interface JobModifyInfoMapper {
    int deleteByPrimaryKey(String jobModifyId);

    int insert(JobModifyInfo record);

    int insertSelective(JobModifyInfo record);

    JobModifyInfo selectByPrimaryKey(String jobModifyId);

    int updateByPrimaryKeySelective(JobModifyInfo record);

    int updateByPrimaryKey(JobModifyInfo record);
}