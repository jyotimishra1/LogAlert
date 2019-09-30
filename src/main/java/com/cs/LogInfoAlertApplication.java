package com.cs;

import com.cs.model.LogInfo;
import com.cs.service.LogFileProcessorService;
import com.cs.service.LogFileProcessorServiceImp;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class LogInfoAlertApplication implements CommandLineRunner {
	@Autowired
	LogFileProcessorService logFileProcessorService;
	private static final Logger logger = LoggerFactory.getLogger(LogInfoAlertApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LogInfoAlertApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) {
		try {
			// Read the file and calculate the event duration and save in HSQLDB
			logFileProcessorService.readFileAndUpdateDb("FILE.DAT");
		} catch (Exception e) {
			logger.error(String.valueOf(e.getStackTrace()));
		}
	}
}
