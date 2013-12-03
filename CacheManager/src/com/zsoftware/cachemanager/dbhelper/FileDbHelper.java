package com.zsoftware.cachemanager.dbhelper;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zsoftware.cachemanager.util.ApplicationUtils;
import com.zsoftware.cachemanager.util.IOUtils;

public class FileDbHelper extends SQLiteOpenHelper {
	private final static String	DEBUG_TAG	= "FileDbHelper";
	private File				f			= null;

	public FileDbHelper(Context context, String FileName,
			CursorFactory factory, int version) throws IOException {

		super(context, FileName, factory, version);

		if (!IOUtils.sdCardExist()) {
			throw new IOException("没有找到sdCard无法创建本地数据库");
		}

		// 存放目录
		String filepath = IOUtils.sdCardRootPath() + java.io.File.separator
				+ ApplicationUtils.getApplicationName(context);
		Log.d(DEBUG_TAG, filepath);

		IOUtils.checkDirExist(filepath, true);
		String DBFileName = filepath + java.io.File.separator + FileName;
		f = new File(DBFileName);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db = SQLiteDatabase.openOrCreateDatabase(f, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		File f = new File("/sdcard/dbfile/mynote.db");
//		if (f.exists()) {
//			f.delete();
//		}
//		db = SQLiteDatabase.openOrCreateDatabase(f, null);
//		onCreate(db);
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(f, null);
		return db;
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(f, null);
		return db;
	}
}
