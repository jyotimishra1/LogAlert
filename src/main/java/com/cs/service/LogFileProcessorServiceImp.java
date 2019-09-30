package com.cs.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.cs.dao.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs.model.LogInfo;

@Service
public class LogFileProcessorServiceImp implements LogFileProcessorService {
	private static final Logger logger = LoggerFactory.getLogger(LogFileProcessorServiceImp.class);
	@Autowired
	FileReader fileReader;

	@Override
	public int readFileAndUpdateDb(String fileName) throws IOException {
		List<LogInfo> companyList = null;
		int recordProcesses = 0;
		if (fileName != null) {
			try {
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource(fileName).getFile());
				recordProcesses = fileReader.readFile(file);
			} catch (IOException e) {
				logger.debug(Arrays.toString(e.getStackTrace()));
			}
		}
		return recordProcesses;
	}
}
