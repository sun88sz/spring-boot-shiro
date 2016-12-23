package com.sun.springboot.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sun
 * @version 2016年3月31日 整理重复,添加强制 东八区
 */
public class DateUtils {

	// 时分秒毫秒
	private static SimpleDateFormat sdfMsFull = new SimpleDateFormat("yyyyMMddHHmmssms");

	// 年月日时分秒
	private static SimpleDateFormat sdfFull = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sdfFullHyphen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfFullSlash = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat sdfMMDD = new SimpleDateFormat("MM月dd日");
	private static SimpleDateFormat sdfMMDDHHMM = new SimpleDateFormat("MM月dd日 HH:mm");

	// 年月日时分
	private static SimpleDateFormat sdfHHmmHyphen = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static SimpleDateFormat sdfHHmmSlash = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	// 年月日
	private static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfDayHyphen = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfDaySlash = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat sdfyyDay = new SimpleDateFormat("yyMMdd");

	// 年月
	private static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyy-MM");

	// 时分
	private static SimpleDateFormat sdfOnlyHHmm = new SimpleDateFormat("HH:mm");
	
	// 月份三字码匹配
	public static Map<String, String> monthMapping = new HashMap<String, String>();

	static {

		sdfMsFull.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfFull.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfFullHyphen.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfFullSlash.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfHHmmHyphen.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfHHmmSlash.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfDay.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfDayHyphen.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfDaySlash.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfyyDay.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfYearMonth.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		sdfOnlyHHmm.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		monthMapping.put("JAN", "01");
		monthMapping.put("FEB", "02");
		monthMapping.put("MAR", "03");
		monthMapping.put("APR", "04");
		monthMapping.put("MAY", "05");
		monthMapping.put("JUN", "06");
		monthMapping.put("JUL", "07");
		monthMapping.put("AUG", "08");
		monthMapping.put("SEP", "09");
		monthMapping.put("OCT", "10");
		monthMapping.put("NOV", "11");
		monthMapping.put("DEC", "12");
	}

	/**
	 * 格式化日期 <br>
	 * 22NOV2015 --> 20151122
	 * 
	 * @param date
	 * @return
	 */
	public static String changeDate(String date) {
		if (date != null && date.length() == 9) {
			String temp = null;
			temp = date.substring(5, 9) + monthMapping.get(date.substring(2, 5)) + date.substring(0, 2);
			return temp;
		}
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyyMMddHHmmssms
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMsFull(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfMsFull.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyyMMddHHmmssms
	 * 
	 * @param dateStr
	 * @return date
	 * @throws ParseException
	 */
	public static Date parseMsFull(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfMsFull.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatSecFull(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfFull.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyyMMddHHmmss
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseSecFull(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfFull.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatSecFullHyphen(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfFullHyphen.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseSecFullHyphen(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfFullHyphen.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy/MM/dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatSecFullSlash(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfFullSlash.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy/MM/dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseSecFullSlash(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfFullSlash.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMinFullHyphen(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfHHmmHyphen.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy-MM-dd HH:mm
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseMinFullHyphen(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfHHmmHyphen.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy/MM/dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMinFullSlash(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfHHmmSlash.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy/MM/dd HH:mm
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseyMinFullSlash(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfHHmmSlash.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDayFull(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfDay.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyyMMdd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseyDayFull(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfDay.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDayFullHyphen(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfDayHyphen.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDayFullHyphen(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfDayHyphen.parse(dateStr);
		return date;
	}

	/**
	 * 转换日期
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDayDateMonth15(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		dateStr = dateStr + "-15";
		Date date = sdfDayHyphen.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy/MM/dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDayFullSlash(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfDaySlash.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy/MM/dd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDayFullSlash(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfDaySlash.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String formateYYMMDD(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfyyDay.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyMMdd
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseYYMMDD(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfyyDay.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 日期转换Date->String<br>
	 * yyyy-MM
	 * 
	 * @param date
	 * @return
	 */
	public static String formatYearMonthDate(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfYearMonth.format(date);
		return dateStr;
	}

	/**
	 * 日期转换String->Date<br>
	 * yyyy-MM
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseYearMonthDate(String dateStr) throws ParseException {
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfYearMonth.parse(dateStr);
		return date;
	}

	/**
	 * 日期转换String -> Date<br>
	 * MM-DD
	 * @param dateStr
	 * @return
	 * @throws ParseException
     */
	public static Date parseMonthDate(String dateStr) throws ParseException{
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfOnlyHHmm.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------
	/**
	 * 日期转换String->Date<br>
	 * HH:mm
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseOnlyHHMMDate(String dateStr) throws ParseException{
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		Date date = sdfOnlyHHmm.parse(dateStr);
		return date;
	}
	/**
	 * 日期转换Date->String<br>
	 * HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatOnlyHHMMDate(Date date) {
		if (date == null || "".equals(date)) {
			return null;
		}
		String dateStr = sdfOnlyHHmm.format(date);
		return dateStr;
	}
	
	/**
	 * 日期转换 Date --> String<br>
	 * MM月dd日
	 * @param date
	 * @return
	 */
	public static String formateOnlyMMDD(Date date){
		if(date == null || "".equals(date)){
			return null;
		}
		String str = sdfMMDD.format(date);
		return str;
	}

	/**
	 * 日期转换 String --> Date<br>
	 * MM月dd日
	 * @param dateStr
	 * @return
	 */
	public static Date formateOnlyMMDD(String dateStr) throws ParseException{
		if(dateStr == null || "".equals(dateStr)){
			return null;
		}
		Date date = sdfMMDD.parse(dateStr);
		return date;
	}
	
	/**
	 * 日期转换 Date --> String<br>
	 * MM月dd日 HH:mm
	 * @param date
	 * @return
	 */
	public static String formateOnlyMMDDHHMM(Date date){
		if(date == null || "".equals(date)){
			return null;
		}
		String str = sdfMMDDHHMM.format(date);
		return str;
	}

	/**
	 * 日期转换 Date --> String<br>
	 * MM月dd日 HH:mm
	 * @param dateStr
	 * @return
	 */
	public static Date formateOnlyMMDDHHMM(String dateStr) throws ParseException{
		if(dateStr == null || "".equals(dateStr)){
			return null;
		}
		Date date = sdfMMDDHHMM.parse(dateStr);
		return date;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int getDaysBetweenDates(Date startDate, Date endDate) throws ParseException {
		Long startTime = parseDayFullHyphen(formatDayFullHyphen(startDate)).getTime();
		Long endTime = parseDayFullHyphen(formatDayFullHyphen(endDate)).getTime();
		int betweenDays = (int) ((endTime - startTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}

	public static String getTimeBetweenDates(Date start, Date end) {
		if (start == null || end == null) {
			return "";
		}
		long diff = end.getTime() - start.getTime();// 这样得到的差值是微秒级别
		if (diff < 0) {
			return "";
		}
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		long second = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
		StringBuilder builder = new StringBuilder();
		if (days != 0) {
			builder.append(days + "天");
		}
		if (hours != 0) {
			builder.append(hours + "小时");
		}
		if (minutes != 0) {
			builder.append(minutes + "分");
		}
		if (second != 0) {
			builder.append(second + "秒");
		}

		return builder.toString();
	}

	/**
	 * @param specifiedDay
	 * @return String
	 * @Title: getSpecifiedDayAfter
	 * @Description: 获得指定日期的后一天
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * @param date
	 * @return String
	 * @Title: getSpecifiedDayAfter
	 * @Description: 获得指定日期的后一天
	 */
	public static Date getDateAfter(Date date) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date dateAfter = new Date(calendar.getTimeInMillis());
		return dateAfter;
	}

	/**
	 * Adds a number of days to a date returning a new object. The original date
	 * object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * Adds to a date returning a new object. The original date object is
	 * unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param calendarField
	 *            the calendar field to add to
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 * @deprecated Will become privately scoped in 3.0
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * Adds a number of seconds to a date returning a new object. The original
	 * date object is unchanged.
	 * 
	 * @param date
	 *            the date, not null
	 * @param amount
	 *            the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *             if the date is null
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 去掉日期的时间( yyyy-mm-dd hh:mm:ss -> yyyy-mm-dd 00:00:00)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayDate(Date date) throws ParseException {
		String dayDate = formatDayFullHyphen(date);
		Date day = parseDayFullHyphen(dayDate);
		return day;
	}

	/**
	 * 计算两个时间相差分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long compareDifferMin(Date date1, Date date2) {
		Long time1 = date1.getTime();
		Long time2 = date2.getTime();
		Long diff;
		Long diffMin;
		if (time1 > time2) {
			diff = time1 - time2;
		} else {
			diff = time2 - time1;
		}

		diffMin = diff / (60 * 1000);
		return diffMin;
	}
	
	/**
	 * 计算两个时间相差秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long compareDifferSec(Date date1, Date date2) {
		Long time1 = date1.getTime();
		Long time2 = date2.getTime();
		Long diff;
		Long diffSec;
		if (time1 > time2) {
			diff = time1 - time2;
		} else {
			diff = time2 - time1;
		}

		diffSec = diff / 1000;
		return diffSec;
	}

	public static int getdayByDate(String date) {
		Calendar calendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date day = null;
		try {
			day = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(day);

		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		if (w == 0) {
			w = 7;
		}

		return w;
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay(Date currentDate) {
		if (null == currentDate) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.setTime(currentDate);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		return firstDay;
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay(Date currentDate) {
		if (null == currentDate) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(currentDate);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		// System.out.println("-----2------lastDay:" + lastDay);
		return lastDay;
	}

	/**
	 * 将秒转换为时分秒
	 * 
	 * @param time
	 * @return
	 */
	public static String secToTime(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	private static String unitFormat(long i) {
		String retStr;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * 日期上加上相应的月份数
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addDateByMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		Date nowDate = cal.getTime();
		return nowDate;
	}

	/**
	 * 获取某月首日
	 * 
	 * @param date
	 *
	 * @return
	 */
	public static Date getCurrentMonthFirstDay(Date date) {
		String dateStr = formatYearMonthDate(date);
		try {
			date = parseYearMonthDate(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}

}