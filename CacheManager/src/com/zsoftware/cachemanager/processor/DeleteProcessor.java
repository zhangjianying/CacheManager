package com.zsoftware.cachemanager.processor;

import android.util.Log;

import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.metadata.Metadata;
import com.zsoftware.cachemanager.util.SqlUtils;

public class DeleteProcessor<T> {
	private final static String	DEBUG_TAG			= "DeleteProcessor";
	private Class<T>			_clazz;
	private StringBuilder		sqlStringBuilder	= new StringBuilder(65);
	private FileDbHelper		fileDbHelper;
	private Metadata			metadata;

	public DeleteProcessor(Class<T> t, FileDbHelper fileDbHelper) {
		this.fileDbHelper = fileDbHelper;
	}

	public DeleteProcessor delete( Class<T> clazz, Metadata metadata) {
		_clazz = clazz;

		sqlStringBuilder.append(String.format(" delete from %s ",
				metadata.tableName));

		this.metadata = metadata;
		return this;
	}

	public DeleteProcessor where(String expression, String... value) {
		sqlStringBuilder.append(" where ");

		expression = expression.replaceAll("\\?", "%s");
		sqlStringBuilder.append(String.format(expression, value));
		return this;
	}

	public void commit() {
		Log.v(DEBUG_TAG, "commit");
		String sql = sqlStringBuilder.toString();
		Log.d(DEBUG_TAG, "sql=" + sql);
		SqlUtils.execSQL(sql, fileDbHelper);
	}
}
