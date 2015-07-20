package com.zsoftware.cachemanager.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
	
	 public static byte[] gZip(byte[] data)
	  {
	    byte[] b = null;
	    try {
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      GZIPOutputStream gzip = new GZIPOutputStream(bos);
	      gzip.write(data);
	      gzip.finish();
	      gzip.close();
	      b = bos.toByteArray();
	      bos.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return b;
	  }

	  public static byte[] unGZip(byte[] data)
	  {
	    byte[] b = null;
	    try {
	      ByteArrayInputStream bis = new ByteArrayInputStream(data);
	      GZIPInputStream gzip = new GZIPInputStream(bis);
	      byte[] buf = new byte[1024];
	      int num = -1;
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      while ((num = gzip.read(buf, 0, buf.length)) != -1) {
	        baos.write(buf, 0, num);
	      }
	      b = baos.toByteArray();
	      baos.flush();
	      baos.close();
	      gzip.close();
	      bis.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return b;
	  }
}
