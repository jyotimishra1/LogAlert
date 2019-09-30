package com.cs.service;

import com.cs.entity.LogInfoEntity;
import com.cs.repository.LogInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
@RunWith( SpringRunner.class)
@SpringBootTest
public class LogFileProcessorServiceTest {
    @Autowired
    LogFileProcessorService logFileProcessorService;
    @Autowired
    LogInfoRepository logInfoRepository;

    @Test
    public void readFileAndUpdateDbTest() throws IOException {
        int recordProcesses=logFileProcessorService.readFileAndUpdateDb("FILEActual.DAT");
        Optional<LogInfoEntity> logInfoEntity=logInfoRepository.findById("scsmbstgra1");
        LogInfoEntity logInfo=logInfoEntity.get();
        assertEquals("scsmbstgra1", logInfo.getEventId());
    }
}
