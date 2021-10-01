package com.biz.tour.service.util;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class GetFolderSize {
	public static long getDirectorySizeCommonIO(File dir) {

	      return FileUtils.sizeOfDirectory(dir);

	  }

}
