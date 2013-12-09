package com.zsoftware.cachemanager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.zsoftware.cachemanager.annotation.Type;
import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.metadata.Metadata;

public class SqlUtils {
	private final static String	DEBUG_TAG	= "SqlUtils";

	public static void execSQL(String sql , FileDbHelper sqlDataBase){
		SQLiteDatabase writableDatabase = sqlDataBase.getWritableDatabase();
		writableDatabase.execSQL(sql);
		writableDatabase.close();
	}
	
	public static void createTable(Metadata metadata, FileDbHelper sqlDataBase) {
		SQLiteDatabase writableDatabase = sqlDataBase.getWritableDatabase();
		String createTableSql = createTableSql(metadata);

		writableDatabase.execSQL(createTableSql);
		writableDatabase.close();
	}

	public static void saveObj(Metadata metadata,
			HashMap<String, Object> valuesMap, FileDbHelper sqlDataBase) {
		SQLiteDatabase writableDatabase = sqlDataBase.getWritableDatabase();
		String insertTableSql = insertTableSql(metadata, valuesMap);
		writableDatabase.execSQL(insertTableSql);
		writableDatabase.close();
	}

	private static String insertTableSql(Metadata metadata,
			HashMap<String, Object> valuesMap) {
		String insertTemplate = "insert into  %s(%s) values(%s)";
		Iterator iter = metadata.field.entrySet().iterator();

		ArrayList<String> fieldsList = new ArrayList<String>();
		ArrayList<String> valuesList = new ArrayList<String>();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = String.valueOf(entry.getKey());
			Type val = (Type) entry.getValue();

			fieldsList.add(key);
			switch (val) {
			case INTEGER:
				valuesList.add(String.valueOf(valuesMap.get(key)));
				break;
			case REAL:
				valuesList.add(String.valueOf(valuesMap.get(key)));
				break;
			case TEXT:
				valuesList.add("'" + String.valueOf(valuesMap.get(key)) + "'");
				break;
			case BLOB:
				valuesList.add("X'" + String.valueOf(valuesMap.get(key)) + "'");
				break;
			}
		}

		String sql = String.format(insertTemplate, metadata.tableName,
				TextUtils.join(",", fieldsList.toArray()),
				TextUtils.join(",", valuesList.toArray()));
		Log.d(DEBUG_TAG, sql);
		return sql;
	}

	/**
	 * 生成建表语句
	 * 
	 * @param metadata
	 * @return
	 */
	private static String createTableSql(Metadata metadata) {

		Iterator iter = metadata.field.entrySet().iterator();
		String ColumnTemplate = " %s %s ";
		ArrayList ColumnList = new ArrayList();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = String.valueOf(entry.getKey());
			Type val = (Type) entry.getValue();

			String DBTypeTemplate = "%s";
			String realDBType = "";
			switch (val) {
			case INTEGER:
				realDBType = String.format(DBTypeTemplate, " Integer ");
				break;
			case REAL:
				realDBType = String.format(DBTypeTemplate, " REAL ");
				break;
			case TEXT:
				realDBType = String.format(DBTypeTemplate, " Text ");
				break;
			case BLOB:
				realDBType = String.format(DBTypeTemplate, " BLOB ");
				break;
			}

			ColumnList.add(String.format(ColumnTemplate, key, realDBType));
		}

		String createTableSql = "create table %s ( %s )";
		String retVal = String.format(createTableSql, metadata.tableName,
				TextUtils.join(",", ColumnList.toArray()));
		Log.d(DEBUG_TAG, retVal);
		return retVal;
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param tableName
	 * @param sqlDataBase
	 * @return
	 */
	public static boolean isExistTable(String tableName,
			FileDbHelper sqlDataBase) {
		boolean retVal = false;
		SQLiteDatabase readableDatabase = sqlDataBase.getReadableDatabase();
		Cursor cursor = readableDatabase
				.rawQuery(
						String.format(
								"SELECT count(*) FROM sqlite_master WHERE type='table' AND name='%s'",
								tableName), null);
		if (cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count > 0) {
				retVal = true;
			}
		}
		cursor.close();
		cursor.close();
		return retVal;
	}

	public static List<HashMap> queryList(String sql, FileDbHelper sqlDataBase,
			Metadata metadata) {
		Log.d(DEBUG_TAG, "queryList()");
		SQLiteDatabase readableDatabase = sqlDataBase.getReadableDatabase();
		Cursor cursor = readableDatabase.rawQuery(sql, null);
		List<HashMap> rowList = new ArrayList<HashMap>();
		while (cursor.moveToNext()) {
			HashMap<String, String> columnMap = new HashMap<String, String>();
			String[] columnNames = cursor.getColumnNames();
			for (String columnname : columnNames) {
				Log.d(DEBUG_TAG, "columnname" + columnname);

				columnMap.put(columnname,
						cursor.getString(cursor.getColumnIndex(columnname)));
			}
			rowList.add(columnMap);
		}
		cursor.close();
		readableDatabase.close();
		return rowList;
	}

}
