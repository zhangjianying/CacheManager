package com.zsoftware.cachemanager.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class BeanUtils {
	private final static String	DEBUG_TAG	= "BeanUtils";

	public static <T> List CopyBeanList(Class<T> clazz, List<HashMap> dataList)
			throws InstantiationException, IllegalAccessException {
		// 枚举类的成员
		Field[] fields = clazz.getFields();
		ArrayList<T> list = new ArrayList<T>();
		for (HashMap dataMap : dataList) {
			Object newInstance = ClazzUtils.newInstance(clazz);

			for (Field field : fields) {
				Log.d(DEBUG_TAG,
						String.format(" filedName: %s  value:%s",
								field.getName(), dataMap.get(field.getName())));

				// field.set(newInstance, dataMap.get(field.getName()));
				setValue(field, newInstance, dataMap.get(field.getName()));
			}
			list.add((T) newInstance);
		}

		return list;

	}

	private static void setValue(Field field, Object obj, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> type = field.getType();
		if (type.getName().equals("java.lang.String")) {
			field.set(obj, value);
		} else if (type.getName().equals("java.lang.Integer")) {
			field.set(obj, Integer.parseInt(String.valueOf(value)));
		} else if (type.getName().equals("java.lang.Long")) {
			field.set(obj, Long.parseLong(String.valueOf(value)));
		} else if (type.getName().equals("java.lang.Double")) {
			field.set(obj, Double.parseDouble(String.valueOf(value)));
		} else if (type.getName().equals("java.lang.Float")) {
			field.set(obj, Float.parseFloat(String.valueOf(value)));
		}
	}
}
