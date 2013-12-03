package com.zsoftware.cachemanager.processor;

import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.metadata.Metadata;
import com.zsoftware.cachemanager.util.BeanUtils;
import com.zsoftware.cachemanager.util.SqlUtils;

public class QueryProcessor<T> {
	private final static String	DEBUG_TAG			= "QueryProcessor";
	private Class<T>			_clazz;
	private StringBuilder		sqlStringBuilder	= new StringBuilder(65);
	private FileDbHelper		fileDbHelper;
	private Metadata			metadata;

	public QueryProcessor(Class<T> t, FileDbHelper fileDbHelper) {
		this.fileDbHelper = fileDbHelper;
	}

	public QueryProcessor query(Class<T> clazz, Metadata metadata) {
		_clazz = clazz;

		sqlStringBuilder.append(String.format("select * from %s",
				metadata.tableName));

		this.metadata = metadata;
		return this;
	}

	public QueryProcessor where(String expression, String... value) {
		sqlStringBuilder.append(" where ");

		expression = expression.replaceAll("\\?", "%s");
		sqlStringBuilder.append(String.format(expression, value));
		return this;
	}

	public List<T> excuteQueryByList() throws InstantiationException,
			IllegalAccessException {
		Log.v(DEBUG_TAG, "excuteQueryByList");
		String sql = sqlStringBuilder.toString();
		Log.d(DEBUG_TAG, "sql=" + sql);

		List<HashMap> queryList = SqlUtils.queryList(sql, fileDbHelper,
				this.metadata);
		Log.d(DEBUG_TAG, "class=" + this._clazz.getName());

		return BeanUtils.CopyBeanList(this._clazz, queryList);
	}
}
