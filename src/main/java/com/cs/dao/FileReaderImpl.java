package com.cs.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cs.model.LogInfo;
import com.cs.service.LogManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
@Repository
public class FileReaderImpl implements FileReader {
    @Autowired
    LogManagerService logManagerService;
    private static final Logger logger = LoggerFactory.getLogger(FileReaderImpl.class);
    private int recordProcesses = 0;

    public int readFile(File file) throws IOException {
        List<LogInfo> logInfoList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream inputStream = null;
        Scanner scanner = null;
        try {
            inputStream = new FileInputStream(file);
            scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    LogInfo newLogInfo = new LogInfo();
                    newLogInfo = mapper.readValue((String) line, LogInfo.class);
                    logInfoList.add(newLogInfo);
                    recordProcesses++;
                } catch (Exception e) {
                    logger.error("Invalid Record found: " + line);
                }
                //Process data in chuck(batch of 1000)
                if (logInfoList.size() == 4) {
                    logManagerService.logInfoProcessor(logInfoList);
                    logInfoList = new ArrayList<>();
                }
            }
            // Process last chunk of data
            logManagerService.logInfoProcessor(logInfoList);
            // note that Scanner suppresses exceptions
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        return recordProcesses;
    }
}
