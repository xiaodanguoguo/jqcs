package com.ebase.core;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期辅助类
 * <p>提供常用的日期－字符串转换</p>
 *
 */
public final class DateHelper extends DateUtils {

	private static Log log = LogFactory.getLog(DateHelper.class);

	public static String datePattern = "yyyy-MM-dd";
	public static String datePattern1 = "yyyy/MM/dd";
	public static String timePattern = datePattern + " HH:mm:ss";
	public static String timePattern2 = datePattern + " HH:mm:ss sss";
	public static String timePattern3 = datePattern1 + " HH:mm:ss";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * 返回日期格式字符串 datePattern (yyyyMMdd)
	 * @return 日期格式字符串 datePattern
	 */
	private static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 格式化当前日期为yyyy-MM-dd HH:mm:ss
	 * @return 当前日期字符串yyyy-MM-dd HH:mm:ss
	 */
	public static final String getCurrDateTime() {
		Date today = new Date();

		SimpleDateFormat df = new SimpleDateFormat(timePattern);

		return df.format(today);
	}

	public static String getStringByDT(Date dt) {
		if (null == dt) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(timePattern2);
		return df.format(dt);
	}

	/**
	 * 格式化当前日期为yyyy-MM-dd
	 * @return 当前日期字符串yyyy-MM-dd
	 */
	public static final String getCurrDate() {
		Date today = new Date();

		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		return df.format(today);
	}

	public static final String getCurrYear() {
		return StringHelper.left(getCurrDate(), 4);
	}

	public static final String getCurrMonth() {
		return StringHelper.mid(getCurrDate(), 5, 2);
	}

	public static final String getCurrDay() {
		return StringHelper.right(getCurrDate(), 2);
	}

	public static final String getWeek() {
		Calendar cal = GregorianCalendar.getInstance();
		return String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
	}

	/**
	 * 根据指定格式将字符串转换为日期
	 * 
	 * @param strDate 日期字符串
	 * @param aMask 输入字符的日期格式
	 * @return java.util.Date
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String strDate, String aMask) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 使用yyyy-MM-dd格式将字符串转换成日期
	 * 
	 * @param strDate the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + datePattern);
			}

			aDate = convertStringToDate(strDate, datePattern);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}

		return aDate;
	}

	/**
	 * 格式化日期为字符串.
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            类型
	 * @return 结果字符串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用yyyy-MM-dd格式化日期
	 * @param aDate java.util.Date
	 * @return yyyyMMdd格式的日期字符串
	 */
	public static final String formatDate(Date aDate) {
		return formatDate(aDate, datePattern);
	}

	/**
	 * 获取当前日期
	 * @return java.util.Date
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前时间戳
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		Calendar cal = GregorianCalendar.getInstance();
		return new java.sql.Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 按指定的格式sFormat将日期dDate转化为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将字符串按指定格式转换为java.util.Date类型
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date string2Date(String str, String format) {
		Date result = null;
		try {
			DateFormat mFormat = new SimpleDateFormat(format);
			result = mFormat.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将给定时间移动相对月份.
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param amount
	 *            数量
	 * @return 结果日期
	 */
	public static Date moveMonth(Date beginDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);

		cal.add(Calendar.MONTH, amount);

		return cal.getTime();
	}

	/**
	 * 将给定时间移动相对天数.
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param amount
	 *            数量
	 * @return 结果日期
	 */
	public static Date moveDay(Date beginDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);

		cal.add(Calendar.DATE, amount);

		return cal.getTime();
	}

	/**
	 * 将给定字符串时间移动相对月份.
	 * 
	 * @param year
	 *            年字符串
	 * @param month
	 *            月
	 * @param amount
	 *            数量
	 * @return int[ 年, 月 ]
	 */
	public static int[] moveMonth(String year, String month, int amount) {
		Date d = moveMonth(parseDate(year + "/" + month, "yyyy/MM"), amount);

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return new int[] { cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) };
	}

	/**
	 * 解析日期.
	 * 
	 * @param input
	 *            输入字符串
	 * @param pattern
	 *            类型
	 * @return Date 对象
	 */
	public static Date parseDate(String input, String pattern) {
		if (StringHelper.isEmpty(input)) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern);

		try {
			return df.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 格式化日期到日时分秒时间格式的显示. d日 HH:mm:ss
	 * 
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToDHMSString(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("d日 HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 格式化日期到时分秒时间格式的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToHMSString(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 将时分秒时间格式的字符串转换为日期.
	 *
	 * @param input
	 * @return
	 */
	public static Date parseHMSStringToDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 格式化日期到 Mysql 数据库日期格式字符串的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMysqlString(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 将 Mysql 数据库日期格式字符串转换为日期.
	 *
	 * @param input
	 * @return
	 */
	public static Date parseStringToMysqlDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回时间字符串, 可读形式的, M月d日 HH:mm 格式. 2004-09-22, LiuChangjiong
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日 HH:mm");

		return dateFormat.format(date);
	}

	/**
	 * 返回时间字符串, 可读形式的, yy年M月d日HH:mm 格式. 2004-10-04, LiuChangjiong
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToyyMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yy年M月d日HH:mm");

		return dateFormat.format(date);
	}

	/**
	 * 返回时间字符串, 可读形式的, yy年M月d日HH:mm 格式. 2004-10-04, LiuChangjiong
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToyyyyMMddHHmm2(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为中文表示方式的字符串(格式为 yyyy年MM月dd日 HH:mm:ss).
	 */
	public static String dateToChineseString(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为 14 位的字符串(格式为yyyyMMddHHmmss).
	 */
	public static String dateTo14String(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		return dateFormat.format(date);
	}

	/**
	 * 将 14 位的字符串(格式为yyyyMMddHHmmss)转换为日期.
	 */
	public static Date string14ToDate(String input) {
		if (StringHelper.isEmpty(input)) {
			return null;
		}

		if (input.length() != 14) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 判断map里面有没有指定的日期数据，有返回，没有返回null
	 *
	 * @param string
	 * @param map
	 * @return
	 */
	public static Date toDateFromMap(String string, Map<String, Object> map) {

		Date date = null;
		if (map.containsKey(string)) {
			date = new Date();
		}
		return date;
	}

	/**
	 * 将Sting类型格式化成Timestamp格式
	 *
	 * @param stringDate string型的日期
	 */
	public static Timestamp fmtDate2Time(String stringDate) {
		if (stringDate != null) {
			DateFormat format = new SimpleDateFormat(timePattern);
			try {
				Timestamp ts = new Timestamp(format.parse(stringDate).getTime());
				return (ts);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}

	/**
	 * @Description 获得当天零点时间
	 * @author fhl
	 * @date 2016年4月7日 下午7:40:51 
	 * @param date
	 * @return
	 */
	public static int getTodayZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return (int) (c.getTime().getTime() / 1000);
	}

	/**
	 * @Description 获得当23点59分时间
	 * @author fhl
	 * @date 2016年4月7日 下午7:40:51 
	 * @param date
	 * @return
	 */
	public static String getTodayBeforeDawnTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return formatDateToMysqlString(c.getTime());
	}

	/**
	 * @Description 获得当天零点时间返回String类型
	 * @author fhl
	 * @date 2016年4月7日 下午7:40:51 
	 * @param date
	 * @return
	 */
	public static String getTodayZeroTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return formatDateToMysqlString(c.getTime());
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS
					&& strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM
					&& strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断时间是否 在指定时间之前
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isInEndDate(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(startDate);
		end.setTime(endDate);
		int result = start.compareTo(end);
		if (result == 0)
			return false;
		// System.out.println("c1相等c2");
		else if (result < 0)
			return true;
		// System.out.println("c1小于c2");
		else
			return false;
		// System.out.println("c1大于c2");
	}

	/**
	 * 计算两时间之前的天数
	 */
	public static int countDays(Date startDate, Date endDate) {
		long start = endDate.getTime() - startDate.getTime();
		long m = start / (24 * 60 * 60 * 1000);
		return Integer.parseInt(String.valueOf(m));
	}

	/**
	 * 判断时间是否为当前时间 根据不同条件显示时分秒 年月日
	 * 
	 * @param time
	 * @return
	 */
	public static String getFomatFromDateToResultDate(String time) {
		String result = "";
		if (time == null || "".equals(time)) {
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar current = Calendar.getInstance();// 实例化一个日历对象
		// 当前年月日
		Integer currentyear = current.get(Calendar.YEAR);
		Integer currentmonth = current.get(Calendar.MONTH) + 1;
		Integer currentday = current.get(Calendar.DAY_OF_MONTH);// x,y,z分别代表当前年，月，日

		// 组装参数
		Date date;
		try {
			date = sdf.parse(time);
			Calendar from = Calendar.getInstance();// 实例化一个参数对象
			from.setTime(date);
			Integer fromYear = from.get(Calendar.YEAR);
			Integer fromMonth = from.get(Calendar.MONTH) + 1;
			Integer fromDay = from.get(Calendar.DAY_OF_MONTH);// x,y,z分别代表当前年，月，日

			// 判断条件
			Integer flagyear = currentyear - fromYear;
			Integer flagmonth = currentmonth - fromMonth;
			Integer flagday = currentday - fromDay;
			if (flagyear == 0) {// 当前年
				if (flagmonth == 0 && flagday == 0) {// 当天
					sdf = new SimpleDateFormat("HH:mm");
					result = sdf.format(date);// 返回 15:20
				} else { // 非当天
					sdf = new SimpleDateFormat("MM-dd HH:mm");
					result = sdf.format(date);// 返回 09-03 15:20
				}
			} else if (flagyear > 0) {// 小于当前年(过去)
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				result = sdf.format(date);// 返回 09-03 15:20
			} else if (flagyear < 0) {// 未来
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result = sdf.format(date);// 原值返回
			}
		} catch (ParseException e) {
			result = "";
		}
		return result;
	}
}
