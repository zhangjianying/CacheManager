package com.zsoftware.cachemanager.processor;

import java.util.HashMap;

import com.zsoftware.cachemanager.dbhelper.FileDbHelper;
import com.zsoftware.cachemanager.processor.metadata.Metadata;
import com.zsoftware.cachemanager.util.ClazzUtils;
import com.zsoftware.cachemanager.util.SqlUtils;

public class SaveProcessor<T> {

	private final static String	DEBUG_TAG			= "SaveProcessor";
	private Class<T>			_clazz;
	private StringBuilder		sqlStringBuilder	= new StringBuilder(65);
	private FileDbHelper		fileDbHelper;
	private Metadata			metadata;

	public SaveProcessor(Class<T> t, FileDbHelper fileDbHelper) {
		this.fileDbHelper = fileDbHelper;
	}

	public SaveProcessor save(Object obj, Class<T> clazz, Metadata metadata)
			throws IllegalArgumentException, IllegalAccessException {
		_clazz = clazz;
		this.metadata = metadata;

		HashMap<String, Object> fieldValues = ClazzUtils.getFieldValues(obj,
				clazz);
		SqlUtils.saveObj(metadata, fieldValues,this.fileDbHelper);
		return this;
	}
}
