package com.cs.service;

import com.cs.dao.LogInfoDao;
import com.cs.model.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LogManagerServiceImp implements LogManagerService {
    private static final int COMPLETED_EVENT = 2;
    private static final int UNCOMPLETED_EVENT = 1;
    private static final String STARTED_EVENT = "STARTED";
    @Autowired
    LogInfoDao logInfoDao;
    private static final Logger logger = LoggerFactory.getLogger(LogManagerServiceImp.class);

    public void logInfoProcessor(List<LogInfo> logInfoList) {
        List<LogInfo> completedStateLogList = new ArrayList<>();
        List<LogInfo> uncompletedStateLogList = new ArrayList<>();
        if (logInfoList != null && logInfoList.size() > 0) {
            logger.debug("Number of records in LogList are =" + logInfoList.size());
            Map<String, List<LogInfo>> eventLogInfoMap = eventIdMapper(logInfoList);
            eventLogInfoMap.forEach((logId, eventLogInfoList) -> {
                //sorting the event by its timestamp
                eventLogInfoList.sort(Comparator.comparing(LogInfo::getTimestamp));
                if (eventLogInfoList.size() == COMPLETED_EVENT) {
                    //logs for which START and FINISH event completed
                    completedStateLogInfoUpdate(logId, eventLogInfoList, completedStateLogList);
                } else if (eventLogInfoList.size() == UNCOMPLETED_EVENT) {
                    //logs for which only one event(START or FINISH) completed
                    uncompletedStateLogInfoUpdate(logId, eventLogInfoList, uncompletedStateLogList);
                } else
                    logger.error("invalid record found for event id:" + eventLogInfoList.get(0).getId());
            });
        } else
            logger.debug("No records Present in Log List");
        saveCompletedStateLogListInDB(completedStateLogList);
        saveUncompletedStateLogListInDB(uncompletedStateLogList);
    }
    //calculate the event duration and updated the logs
    void completedStateLogInfoUpdate(String logId, List<LogInfo> eventLogInfoList, List<LogInfo> completedStateLogList) {
        long eventDuration = eventLogInfoList.get(1).getTimestamp() - eventLogInfoList.get(0).getTimestamp();
        eventLogInfoList.get(0).setEventDuration(Math.abs(eventDuration));
        eventLogInfoList.get(0).setStartTime(eventLogInfoList.get(0).getTimestamp());
        eventLogInfoList.get(0).setEndTime(eventLogInfoList.get(1).getTimestamp());
        if (Math.abs(eventDuration) > 4) {
            eventLogInfoList.get(0).setAlert(true);
        } else
            eventLogInfoList.get(0).setAlert(false);
        completedStateLogList.add(eventLogInfoList.get(0));
    }

    void uncompletedStateLogInfoUpdate(String logId, List<LogInfo> eventLogInfoList, List<LogInfo> uncompletedStateLogList) {
        LogInfo uncompletedStateLog = eventLogInfoList.get(0);
        if (uncompletedStateLog.getState().equals(STARTED_EVENT)) {
            uncompletedStateLog.setStartTime(uncompletedStateLog.getTimestamp());
        } else
            uncompletedStateLog.setEndTime(uncompletedStateLog.getTimestamp());
        uncompletedStateLogList.add(uncompletedStateLog);
    }

    @Override
    public void saveCompletedStateLogListInDB(List<LogInfo> completedStateLogList) {
        if (completedStateLogList.size() > 0)
            logInfoDao.writeCompletedLogInfoToDb(completedStateLogList);
    }

    @Override
    public void saveUncompletedStateLogListInDB(List<LogInfo> uncompletedStateLogList) {
        if (uncompletedStateLogList.size() > 0)
            logInfoDao.writeUncompletedLogInfoToDb(uncompletedStateLogList);
    }

    //create map of event id
    public Map<String, List<LogInfo>> eventIdMapper(List<LogInfo> logInfoList) {
        Map<String, List<LogInfo>> flagLogInfoListMap = new HashMap<>();
        logInfoList.forEach(logInfo -> {
            List<LogInfo> tempLogInfoList = new ArrayList<>();
            tempLogInfoList.add(logInfo);
            if (!flagLogInfoListMap.containsKey(logInfo.getId())) {
                flagLogInfoListMap.put(logInfo.getId(), tempLogInfoList);
            } else {
                flagLogInfoListMap.get(logInfo.getId()).add(logInfo);
            }
        });
        return flagLogInfoListMap;
    }
}
