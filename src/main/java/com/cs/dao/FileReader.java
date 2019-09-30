package com.cs.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cs.model.LogInfo;

public interface FileReader {
	public int readFile(File file) throws IOException;
}
