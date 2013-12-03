package com.zsoftware.cachemanager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	Type type() default Type.TEXT;

	boolean isIndex() default false;

	boolean encrypt() default false;
}
