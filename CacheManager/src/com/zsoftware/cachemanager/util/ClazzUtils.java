package com.zsoftware.cachemanager.util;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.zsoftware.cachemanager.annotation.Column;
import com.zsoftware.cachemanager.annotation.Table;
import com.zsoftware.cachemanager.annotation.Type;

public class ClazzUtils {
	public static String getTableName(Class<?> clazz) throws Exception {
		boolean annotationPresent = clazz.isAnnotationPresent(Table.class);
		if (annotationPresent) {
			Table annotation = clazz.getAnnotation(Table.class);
			return annotation.value();
		}
		throw new Exception("类未打标注");
	}

	public static HashMap<String, Type> getIndexField(Class<?> clazz) {

		HashMap<String, Type> typs = new HashMap<String, Type>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column annotation = field.getAnnotation(Column.class);
				if (annotation.isIndex())
					typs.put(field.getName(), annotation.type());
			}

		}

		return typs;
	}

	public static HashMap<String, Type> getField(Class<?> clazz) {

		HashMap<String, Type> typs = new HashMap<String, Type>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column annotation = field.getAnnotation(Column.class);
				typs.put(field.getName(), annotation.type());
			}

		}

		return typs;
	}

	public static HashMap<String, Object> getFieldValues(Object obj, Class clazz)
			throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, Object> retVal = new HashMap<String, Object>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Column.class)) {
				Column annotation = field.getAnnotation(Column.class);
				// 加密
				retVal.put(field.getName(), field.get(obj));
			}

		}
		return retVal;
	}

	public static <T> Object newInstance(Class<T> clazz)
			throws InstantiationException, IllegalAccessException {

		return clazz.newInstance();
	}
}
