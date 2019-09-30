package com.cs.service;

import com.cs.model.LogInfo;

import java.util.List;

public interface LogManagerService {
    public void logInfoProcessor(List<LogInfo> logInfoList);

    public void saveCompletedStateLogListInDB(List<LogInfo> logInfoList);

    public void saveUncompletedStateLogListInDB(List<LogInfo> logInfoList);
}
