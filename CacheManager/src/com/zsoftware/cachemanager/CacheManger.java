package com.zsoftware.cachemanager;

import java.util.HashMap;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.DeleteProcessor;
import com.zsoftware.cachemanager.processor.QueryProcessor;
import com.zsoftware.cachemanager.processor.SaveProcessor;
import com.zsoftware.cachemanager.processor.UpdateProcessor;
import com.zsoftware.cachemanager.processor.metadata.Metadata;
import com.zsoftware.cachemanager.util.ClazzUtils;
import com.zsoftware.cachemanager.util.SqlUtils;

public class CacheManger {
	private final static String			DEBUG_TAG		= "CacheManger";
	private FileDbHelper				sqliteopenhelper;
	private HashMap<String, Metadata>	MetadataHashMap	= new HashMap<String, Metadata>();

	public CacheManger(SQLiteOpenHelper sqliteopenhelper) {
		this.sqliteopenhelper = (FileDbHelper) sqliteopenhelper;
	}

	public QueryProcessor query(Class clazz) throws Exception {
		checkHaveMetaData(clazz);

		return new QueryProcessor(clazz, sqliteopenhelper).query(clazz,
				MetadataHashMap.get(clazz.getName()));
	}

	public SaveProcessor save(Object obj) throws Exception {
		Class clazz = obj.getClass();
		checkHaveMetaData(clazz);
		return new SaveProcessor(clazz, sqliteopenhelper).save(obj, clazz,
				MetadataHashMap.get(clazz.getName()));
	}

	public DeleteProcessor delete(Class clazz) throws Exception {
		checkHaveMetaData(clazz);
		return new DeleteProcessor(clazz, sqliteopenhelper).delete(clazz,
				MetadataHashMap.get(clazz.getName()));
	}
	
	public UpdateProcessor update(Class clazz) throws Exception {
		    checkHaveMetaData(clazz);

		    return new UpdateProcessor(clazz, this.sqliteopenhelper).update(clazz, 
		      (Metadata)this.MetadataHashMap.get(clazz.getName()));
	}


	private void checkHaveMetaData(Class<?> clazz) throws Exception {
		Log.v(DEBUG_TAG, "checkHaveMetaData");

		if (!MetadataHashMap.containsKey(clazz.getName())) {
			String tableName = ClazzUtils.getTableName(clazz);
			Log.i(DEBUG_TAG, tableName);
			Metadata metadata = new Metadata();
			metadata.tableName = tableName;
			metadata.field = ClazzUtils.getField(clazz);
			if (!SqlUtils.isExistTable(tableName, sqliteopenhelper)) {
				Log.v(DEBUG_TAG, "表不存在需要创建表");
				SqlUtils.createTable(metadata, sqliteopenhelper);
				SqlUtils.createIndex(metadata, ClazzUtils.getIndexField(clazz),
						sqliteopenhelper);
			}

			MetadataHashMap.put(clazz.getName(), metadata);
		}

	}
}
