package com.cs.dao;

import com.cs.model.LogInfo;

import java.util.List;

public interface LogInfoDao {
    public void writeCompletedLogInfoToDb(List<LogInfo> alertLogInfoList);
    public void writeUncompletedLogInfoToDb(List<LogInfo> alertLogInfoList);
}
