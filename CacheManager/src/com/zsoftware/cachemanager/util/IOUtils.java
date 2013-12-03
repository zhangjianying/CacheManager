package com.zsoftware.cachemanager.util;

import java.io.File;

import android.os.Environment;

public class IOUtils {

	/*
	 * 判断sdCard是否存在
	 */
	public static boolean sdCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/*
	 * 获取sdCard根目录
	 */
	public static String sdCardRootPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 检查文件价是否存在
	 */
	public static void checkDirExist(String path, boolean isCreate) {
		File fileDir = new File(path);

		if (isCreate && !fileDir.exists()) {
			fileDir.mkdir();
		}

	}
}
