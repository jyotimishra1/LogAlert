package com.cs.dao;

import com.cs.entity.LogInfoEntity;
import com.cs.model.LogInfo;
import com.cs.repository.LogInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LogInfoDaoImp implements LogInfoDao {
    @Autowired
    LogInfoRepository logInfoRepository;

    public void writeCompletedLogInfoToDb(List<LogInfo> alertLogInfoList) {
        List<LogInfoEntity> logInfoEntityList = adaptLogEntity(alertLogInfoList);
        //saving LogInfoEntity list in HSQLDB
        logInfoRepository.saveAll(logInfoEntityList);
    }

    public void writeUncompletedLogInfoToDb(List<LogInfo> uncompletedLogInfoList) {
        //converting LogInfo to LogInfoEntity
        List<LogInfoEntity> uncompletedLogList = adaptLogEntity(uncompletedLogInfoList);
        List<LogInfoEntity> logInfoEntityUpdateList = new ArrayList<>();
        List<LogInfoEntity> logInfoEntityInsertList = new ArrayList<>();
        List<String> ids = uncompletedLogList.stream().map(LogInfoEntity::getEventId).collect(Collectors.toList());
        //Getting Uncompleted log list from DB
        Iterable<LogInfoEntity> UncompletedDBLog = logInfoRepository.findAllById(ids);
        List<LogInfoEntity> UncompletedDBLogList = new ArrayList<>();
        UncompletedDBLog.forEach(UncompletedDBLogList::add);
        if (UncompletedDBLogList.size() > 0) {
            for (LogInfoEntity uncompletedFileLog : uncompletedLogList) {
                for (LogInfoEntity UncompletedLogDB : UncompletedDBLogList) {
                    //if Uncompleted log present in database then calculate the event duration and updated the existing data row in DB. Otherwise Insert the log info
                    if (uncompletedFileLog.getEventId().equals(UncompletedLogDB.getEventId())) {
                        long eventDuration = (uncompletedFileLog.getStartTime() + uncompletedFileLog.getEndTime()) - (UncompletedLogDB.getStartTime() + UncompletedLogDB.getEndTime());
                        UncompletedLogDB.setEventDuration(Math.abs(eventDuration));
                        if (Math.abs(eventDuration) > 4) {
                            UncompletedLogDB.setAlert(true);
                        } else
                            UncompletedLogDB.setAlert(false);
                        if (UncompletedLogDB.getStartTime() != 0) {
                            UncompletedLogDB.setEndTime(uncompletedFileLog.getEndTime());
                        } else
                            UncompletedLogDB.setStartTime(uncompletedFileLog.getStartTime());
                        logInfoEntityUpdateList.add(UncompletedLogDB);
                    } else
                        logInfoEntityInsertList.add(uncompletedFileLog);
                }
            }
            logInfoRepository.saveAll(logInfoEntityInsertList);
            logInfoRepository.saveAll(logInfoEntityUpdateList);
        } else
            logInfoRepository.saveAll(uncompletedLogList);
    }

    public List<LogInfoEntity> adaptLogEntity(List<LogInfo> alertLogInfoList) {
        List<LogInfoEntity> logInfoEntityList = new ArrayList<>();
        //converting LogInfo to LogInfoEntity
        for (LogInfo alertLogInfo : alertLogInfoList) {
            LogInfoEntity logInfoEntity = new LogInfoEntity();
            logInfoEntity.setEventId(alertLogInfo.getId());
            logInfoEntity.setType(alertLogInfo.getType());
            logInfoEntity.setHost(alertLogInfo.getHost());
            logInfoEntity.setEventDuration(alertLogInfo.getEventDuration());
            logInfoEntity.setAlert(alertLogInfo.isAlert());
            logInfoEntity.setStartTime(alertLogInfo.getStartTime());
            logInfoEntity.setEndTime(alertLogInfo.getEndTime());
            logInfoEntityList.add(logInfoEntity);
        }
        return logInfoEntityList;
    }
}
