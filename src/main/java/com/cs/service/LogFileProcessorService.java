package com.cs.service;

import com.cs.model.LogInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
public interface LogFileProcessorService {
    public int readFileAndUpdateDb(String fileName) throws IOException;
}
