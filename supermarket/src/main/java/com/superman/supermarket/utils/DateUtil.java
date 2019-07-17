package com.superman.supermarket.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd :HH ss mm");
	
	/**
	 * 	将字符串转时间类型
	 * @param str
	 * @return
	 */
	public static Date str2Date(String str) {
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 	将时间类型转换成字符串
	 * @param date
	 * @return
	 */
	public static String date2Str(Date date) {
		return sdf.format(date);
	}
}
