package com.zsoftware.cachemanager.processor.metadata;

import java.util.HashMap;

import com.zsoftware.cachemanager.annotation.Type;

public class Metadata {
	public String					tableName;
	public HashMap<String, Type>	field	= new HashMap<String, Type>();
}
