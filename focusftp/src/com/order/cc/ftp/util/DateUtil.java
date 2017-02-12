package com.order.cc.ftp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author yxm
 * @date 2016-12-7
 */
public class DateUtil {
	public static final SimpleDateFormat ymd_sdf = new SimpleDateFormat(
			"yyyy/MM/dd");
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyyMMdd");
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		return ymd_sdf.format(new Date());
	}
	
	/**
	 * 时间转字符串
	 * @return
	 */
	public static String date2SStr() 
	{
		return yyyyMMdd.format(new Date());
	}
	/**
	 * 获取昨天日期
	 * @return
	 */
	public static String  getYesterday(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		return yyyyMMdd.format(cal.getTime());
	}
	/**
	 * 获取上个月当天
	 * @return
	 */
	public static String getLastMonth(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH,-1);
		return yyyyMMdd.format(cal.getTime());
	}
	/**
	 * 获取上个月前一天
	 * @return
	 */
	public static String getLastMonthDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		cal.add(Calendar.MONTH,-1);
		return yyyyMMdd.format(cal.getTime());
	}
}
