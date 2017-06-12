package com.renms.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.renms.common.enums.DataSourceEnum;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceFile {
	public DataSourceEnum value() default DataSourceEnum.anquan;
}
