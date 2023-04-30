package com.yanzhen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 把时间转成字符串
	 */
	public  static String dateChangeString(Date date) {
		SimpleDateFormat sdf=
		 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(date);
		return time;
	}

	/**
	 * 把字符串转Date
	 */
	public static Date stringToDate(String time) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
		Date date = simpleDateFormat.parse(time);
		return date;
	}
	

}
