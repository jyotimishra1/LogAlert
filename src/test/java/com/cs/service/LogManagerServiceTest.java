package com.cs.service;

import com.cs.entity.LogInfoEntity;
import com.cs.model.LogInfo;
import com.cs.repository.LogInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
@RunWith( SpringRunner.class)
@SpringBootTest
public class LogManagerServiceTest {
    @Autowired
    LogManagerServiceImp logManagerServic;
    @Autowired
    LogInfoRepository logInfoRepository;

    @Test
    public void logInfoProcessorTest() {
        List<LogInfo> logInfoList = logInfodataCreation();
        logManagerServic.logInfoProcessor(logInfoList);
        Optional<LogInfoEntity> logInfoEntity = logInfoRepository.findById("1");
        LogInfoEntity logInfo = logInfoEntity.get();
        assertEquals("1", logInfo.getEventId());
        assertEquals(5, logInfo.getEventDuration());
    }

    @Test
    public void eventIdMapperTest() throws IOException {
        List<LogInfo> logInfoList = logInfodataCreation();
        Map<String, List<LogInfo>> actual = logManagerServic.eventIdMapper(logInfoList);
        assertEquals(2, actual.size());
    }

    @Test
    public void saveCompletedStateLogListInDBTest() throws IOException {
        List<LogInfo> logInfoList = logInfodataCreation();
        logManagerServic.saveCompletedStateLogListInDB(logInfoList);
        Optional<LogInfoEntity> logInfoEntity = logInfoRepository.findById("1");
        LogInfoEntity logInfo = logInfoEntity.get();
        assertEquals("1", logInfo.getEventId());
    }

    @Test
    public void saveUncompletedStateLogListInDBTest() throws IOException {
        List<LogInfo> logInfoList = logInfodataCreation();
        LogInfo logInfo3 = new LogInfo("3", "STARTED", 12);
        logInfoList.add(logInfo3);
        logManagerServic.saveUncompletedStateLogListInDB(logInfoList);
        Optional<LogInfoEntity> logInfoEntity = logInfoRepository.findById("3");
        LogInfoEntity logInfo = logInfoEntity.get();
        assertEquals("3", logInfo.getEventId());
    }

    List<LogInfo> logInfodataCreation() {
        List<LogInfo> LogInfoList = new ArrayList<>();
        LogInfo logInfo1 = new LogInfo("1", "STARTED", 12);
        LogInfo logInfo2 = new LogInfo("2", "STARTED", 14);
        LogInfo logInfo3 = new LogInfo("1", "FINISHED", 17);
        LogInfo logInfo4 = new LogInfo("2", "FINISHED", 29);
        LogInfoList.add(logInfo1);
        LogInfoList.add(logInfo2);
        LogInfoList.add(logInfo3);
        LogInfoList.add(logInfo4);
        return LogInfoList;
    }
}
