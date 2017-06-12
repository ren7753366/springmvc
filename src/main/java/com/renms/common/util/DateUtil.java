package com.renms.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 获得当前时间
	 * @param format 时间格式  example:yyyy-MM-dd
	 * @return 当前时间字符串
	 */
	public static String getCurrentDateStr(String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获得当前时间字符串，默认格式
	 * @return 默认格式的当前时间字符串
	 */
	public static String getCurrentDateStr(){
		return DEFAULT_FORMAT.format(new Date());
	}
	
	/**
	 * 格式化时间
	 * @param format 时间格式  example:yyyy-MM-dd
	 * @return 当前时间字符串
	 */
	public static String formateDateStr(long date,String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(date));
	}
	
	
	/**
	 * 格式化时间
	 * @param parse 时间格式  example:yyyy-MM-dd
	 * @return 当前时间字符串
	 */
	public static Date parseDateStr(String date,String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
}
