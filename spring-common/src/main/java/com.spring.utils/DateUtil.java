package com.spring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  时间操作类.
 *<P>
 *  1 获取系统当前日期时间<br>
 *  2 时间格式转换<br>
 *  3 获取下一个月的第一天<br>
 *  4 判断闰年<br>
 *  5 获取一个月的最后一天<br>
 *  6 获取下一个月的第一天<br>
 *  7 判断两个时间大小
 *  日期格式化和转换，及时间是否是规范的时间判断
 *<P>
 * @author tangzhengyu
 * @version V1.0 2011-12-22 
 */
public class DateUtil {

	/** 日期 format: yyyyMMddHHmmss. */

	public static final String DATE_FORMATE_STRING_DEFAULT = "yyyyMMddHHmmss";

	/** 日期 format: yyyy-MM-dd HH:mm:ss. */
	public static final String DATE_FORMATE_STRING_A = "yyyy-MM-dd HH:mm:ss";

	/** 日期 format: yyyy-MM-dd. */
	public static final String DATE_FORMATE_STRING_B = "yyyy-MM-dd";

	/** 日期 format: MM/dd/yyyy HH:mm:ss a. */
	public static final String DATE_FORMATE_STRING_C = "MM/dd/yyyy HH:mm:ss a";

	/** 日期 format: yyyy-MM-dd HH:mm:ss a. */
	public static final String DATE_FORMATE_STRING_D = "yyyy-MM-dd HH:mm:ss a";

	/** 日期 format: yyyy-MM-dd'T'HH:mm:ss'Z'. */
	public static final String DATE_FORMATE_STRING_E = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/** 日期 format: yyyy-MM-dd'T'HH:mm:ssZ. */
	public static final String DATE_FORMATE_STRING_F = "yyyy-MM-dd'T'HH:mm:ssZ";

	/** 日期 format: yyyy-MM-dd'T'HH:mm:ssz. */
	public static final String DATE_FORMATE_STRING_G = "yyyy-MM-dd'T'HH:mm:ssz";

	/** 日期 format: yyyyMMdd. */
	public static final String DATE_FORMATE_STRING_H = "yyyyMMdd";

	/** 日期 format: yyyy-MM-dd HH:mm:ss.SSS. */
	public static final String DATE_FORMATE_STRING_I = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 日期 format: yyyyMMddHHmmss.SSS. */
	public static final String DATE_FORMATE_STRING_J = "yyyyMMddHHmmss.SSS";

	/** 日期 format: yyyyMMddHHmmssSSS . */
	public static final String DATE_FORMATE_STRING_K = "yyyyMMddHHmmssSSS";

	/** 日期 format: yyyy-MM. */
	public static final String DATE_FORMATE_STRING_YM = "yyyy-MM";
	
	/** 日期 format. */
	private static Map<String, SimpleDateFormat> formats;
	

	static {
		formats = new HashMap<String, SimpleDateFormat>();

		formats.put(DATE_FORMATE_STRING_DEFAULT, new SimpleDateFormat(DATE_FORMATE_STRING_DEFAULT));
		formats.put(DATE_FORMATE_STRING_A, new SimpleDateFormat(DATE_FORMATE_STRING_A));
		formats.put(DATE_FORMATE_STRING_B, new SimpleDateFormat(DATE_FORMATE_STRING_B));
		formats.put(DATE_FORMATE_STRING_C, new SimpleDateFormat(DATE_FORMATE_STRING_C));
		formats.put(DATE_FORMATE_STRING_D, new SimpleDateFormat(DATE_FORMATE_STRING_D));
		formats.put(DATE_FORMATE_STRING_E, new SimpleDateFormat(DATE_FORMATE_STRING_E));
		formats.put(DATE_FORMATE_STRING_F, new SimpleDateFormat(DATE_FORMATE_STRING_F));
		formats.put(DATE_FORMATE_STRING_G, new SimpleDateFormat(DATE_FORMATE_STRING_G));
		formats.put(DATE_FORMATE_STRING_H, new SimpleDateFormat(DATE_FORMATE_STRING_H));
		formats.put(DATE_FORMATE_STRING_I, new SimpleDateFormat(DATE_FORMATE_STRING_I));
		formats.put(DATE_FORMATE_STRING_J, new SimpleDateFormat(DATE_FORMATE_STRING_J));
		formats.put(DATE_FORMATE_STRING_K, new SimpleDateFormat(DATE_FORMATE_STRING_K));
	}

	/**
	 * 将Date转换为 pattern 格式的字符串 . 
	 *  格式为：yyyyMMddHHmmss<BR>
	 *	yyyy-MM-dd HH:mm:ss <BR>
	 *	yyyy-MM-dd <BR>
	 *	MM/dd/yyyy HH:mm:ss a <BR>
	 *	yyyy-MM-dd HH:mm:ss a <BR>
	 *	yyyy-MM-dd'T'HH:mm:ss'Z' <BR>
	 *	yyyy-MM-dd'T'HH:mm:ssZ <BR>
	 *	yyyy-MM-dd'T'HH:mm:ssz <BR>
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return String --格式化的日期字符串
	 * @see Date
	 */
	public static String getFormatTimeString(Date date, String pattern) {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		//单实例,SimpleDateFormat非线程安全
		synchronized (sDateFormat) {
			return sDateFormat.format(date);
		}
	}

	/**
	 * 将Date转换为默认的YYYYMMDDHHMMSS 格式的字符串.
	 * @param date 日期
	 * @return String 日期
	 */
	public static String getDefaultFormateTimeString(Date date) {
		return getFormatTimeString(date, DATE_FORMATE_STRING_DEFAULT);
	}

	/**
	 * 根据pattern取得的date formate.
	 * <P>
	 * @param pattern 日期格式
	 * @return SimpleDateFormat SimpleDateFormat 对象
	 */
	public static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat sDateFormat = formats.get(pattern);
		if (sDateFormat == null) {
			sDateFormat = new SimpleDateFormat(pattern);
			formats.put(pattern, sDateFormat);
		}
		return sDateFormat;
	}

	/**
	 * 将格式将日期字符串转换为Date对象.
	 * <P>
	 * @param date 字符串
	 * @param pattern 格式如下：
	 * 	yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @return 日期Date对象
	 * @throws ParseException
	 * @see Date
	 */
	public static Date getDateFromString(String date, String pattern) {
		try {
			SimpleDateFormat sDateFormat = getDateFormat(pattern);
			//单实例,SimpleDateFormat非线程安全
			synchronized (sDateFormat) {
				return sDateFormat.parse(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将日期字符串转化成默认格式YYYYMMDDHHMMSS的Date对象.
	 * <P>
	 * @param date 字符串日期
	 * @return Date Date对象日期
	 * @throws ParseException 解析异常
	 */
	public static Date getDefaultDateFromString(String date) throws ParseException {
		return getDateFromString(date, DATE_FORMATE_STRING_DEFAULT);
	}

	/**
	 * 取当前时间,格式为YYYYMMDDHHMMSS的日期字符串.
	 * <P>
	 * @return String 当前日期字符串.
	 * @see Date
	 */
	public static String getNowDefault() {
		return getNow(DATE_FORMATE_STRING_DEFAULT);
	}

	/**
	 * 按照pattern格式取当前日期字符串.
	 * @param pattern	日期字符串格式
	 * @return			格式化后的当前日期字符串
	 */
	public static String getNow(String pattern) {
		return getFormatTimeString(new Date(), pattern);
	}

	/**
	 * 取当前时间,格式为YYYYMMDD.
	 * <P>
	 * @return String 当前日期字符串.
	 * @see Date
	 */
	public static String getNowII() {
		return getFormatTimeString(new Date(), DATE_FORMATE_STRING_H);
	}

	/**
	 * 将输入pattern格式的日期字符串转换为取时间的毫秒数 since 1976.
	 * <P>
	 * @return long 时间毫秒数
	 * @throws ParseException 解析异常
	 * @see Date
	 */
	public static long dateStringToLong(String str, String pattern) throws ParseException {
		return getDateFromString(str, pattern).getTime();
	}

	/**
	 * 把since1976的毫秒数转成默认格式yyyyMMddHHmmss的String日期字符串.
	 * <P>
	 * @param time long 时间
	 * @return String 字符串日期
	 */
	public static String longToDateStringDefault(long time) {
		return getFormatTimeString(new Date(time), DATE_FORMATE_STRING_DEFAULT);
	}

	/**
	 * 将时间的毫秒数 since 1976转换为按照pattern格式的日期字符串.
	 * <P>
	 * @return String 日期字符串
	 * @see Date
	 */
	public static String longToDateString(long time, String pattern) {
		return getFormatTimeString(new Date(time), pattern);
	}

	/**
	 * 将Date对象转成since 1976的毫秒数.
	 * <P>
	 * @param date 日期
	 * @return	since1976的毫秒数
	 */
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	/**
	 * 将since1976毫秒数转成Date对象.
	 * <P>
	 * @param time 时间
	 * @return Date 对象时间
	 */
	public static Date longToDate(long time) {
		return new Date(time);
	}

	/**
	 * 自动适配两种格式的日期字符串转换为date对象.
	 * <P>
	 * A格式	:	yyyy-MM-dd HH:mm:ss<BR>
	 * B格式	:	yyyy-MM-dd<BR>
	 * <P>
	 * @param date 日期
	 * @return Date 返回日期
	 * @throws ParseException
	 */
	public static Date getDateFromStringAdaptTwoPattern(String date) throws ParseException {
		try {
			return getDateFromString(date, DATE_FORMATE_STRING_A);
		} catch (Exception e) {
			return getDateFromString(date, DATE_FORMATE_STRING_B);
		}
	}
	
	
	/**
	 * 将指定字符串格式的时间串转化为需要的格式时间串.
	 * <P>
	 * @param	numdate　入参时间
	 * @param	inFormat 入参格式
	 * @param outFormat　出参格式
	 * @return String 输出日期
	 * @throws ParseException  解析日期异常
	 */
	public static String changeNumDateToDate(String numdate, String inFormat, String outFormat){
		try {
			Date date = getDateFromString(numdate, inFormat);
			return getFormatTimeString(date, outFormat);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取后一个月的第一天.
	 * <P>
	 * @param nowdate 现在日期
	 * @param inFormat	输入格式
	 * @param outFormat 输出格式
	 * @return String  后一个月的第一天日期
	 * @throws ParseException  解析日期异常
	 */
	public static String getNextMonthFistDay(String nowdate,String inFormat,String outFormat) throws ParseException{
		
		Date date = getDateFromString(nowdate, inFormat);
			
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH)+1);
		cl.set(Calendar.DAY_OF_MONTH, 1);	
			
		date = cl.getTime();
		return getFormatTimeString(date,outFormat);
			
	}
	
	/**
	 * 判断闰年.
	 * <P>
	 *  详细设计： <BR>
	 * 	1.被400整除是闰年<BR>
	 * 	2.能被4整除同时不能被100整除则是闰年<BR>
	 * @param year 年
	 * @return boolean true:是闰年
	 */
	public static boolean isLeapYear(int year) {
		boolean isLeapYear = false;
		if ((year % 400) == 0) {
			isLeapYear = true;
		} else if (((year % 4) == 0) && ((year % 100) != 0)) {
			isLeapYear = true;
		}
		
		return isLeapYear;
	}
	
	/**
	 * 获取一个月的最后一天.
	 * <P>
	 * @param nowdate 现在日期
	 * @param inFormat	输入格式
	 * @param outFormat 输出格式
	 * @return String  一个月的最后一天
	 * @throws ParseException  解析日期异常
	 */
	public static String getLastDay(String nowdate,String inFormat,String outFormat) throws ParseException{
		
		String returndate = "";

		Date date = getDateFromString(nowdate, inFormat);
		
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		switch (cl.get(Calendar.MONTH)) {
		case 0:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			int year = cl.get(Calendar.YEAR);
			
			if (isLeapYear(year)) {
				cl.set(Calendar.DAY_OF_MONTH, 29);
			}else{				
				cl.set(Calendar.DAY_OF_MONTH, 28);
			}
			break;
		case 2:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			cl.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			cl.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			cl.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			cl.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			cl.set(Calendar.DAY_OF_MONTH, 31);
			break;
		default:
			break;
		}

		date = cl.getTime();

		returndate = getFormatTimeString(date,outFormat);

		return returndate;
	}
	
	/**
	 * 获取一个月的最后一天.
	 * <P>
	 * @param fmt 日期
	 * @return String 返回最后一天日期
	 */
	public static String getMonthLastDay(String fmt){
		String returndate = "";
		Date date = null;
		Calendar cl = Calendar.getInstance();
		switch (cl.get(Calendar.MONTH)) {
			case 0:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 1:
				int year = cl.get(Calendar.YEAR);

				if (isLeapYear(year)) {
					cl.set(Calendar.DAY_OF_MONTH, 29);
				} else {				
					cl.set(Calendar.DAY_OF_MONTH, 28);
				}
				break;
			case 2:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 3:
				cl.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 4:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 5:
				cl.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 6:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 7:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 8:
				cl.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 9:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			case 10:
				cl.set(Calendar.DAY_OF_MONTH, 30);
				break;
			case 11:
				cl.set(Calendar.DAY_OF_MONTH, 31);
				break;
			default:
				break;
		}

		date = cl.getTime();

		returndate =getFormatTimeString(date,fmt);

		return returndate;
	}
	
	/**
	 * 获取后一个月的第一天.
	 * <P>
	 * @param fmt 日期
	 * @return String 下个月第一天
	 */
	public static String getNextMonthFirstDay(String fmt){
		String returndate = "";
		Date date = null;
			
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.MONTH, cl.get(Calendar.MONTH)+1);
		cl.set(Calendar.DAY_OF_MONTH, 1);	
		
		date = cl.getTime();
		
		returndate = getFormatTimeString(date,fmt);
			
		return returndate;
	}
	
	/**
	 * 判断两个时间大小(fistDate 在 secondDate 之前 返回true，否则返回false).
	 * <P>
	 * @param fistDate 第一个日期
	 * @param secondDate 第二个日期
	 * @param format 格式
	 * @return boolean true:fistDate 在 secondDate 之前 返回true
	 * @throws ParseException 解析异常
	 */
	public static boolean compareDate(String fistDate,String secondDate,String format){
		
		boolean flag = false;
		
		Date fist = null;
		Date second = null;
		
		try {
			fist = getDateFromString(fistDate, format);

			second = getDateFromString(secondDate, format);
			if(fist.before(second)){
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	/**
	 * 计算两日期相差的月份，不足1个月返回0
	 * @param firstDate  第一个日期
	 * @param secondDate 第二个日期
	 * @param format  入参日期格式
	 * @return
	 */
	public static int getDateMinusMonth(String firstDate,String secondDate,String format) {
		int result = 0;
		Date first = null;
		Date second = null;
		try{
			first = getDateFromString(firstDate, format);
			second = getDateFromString(secondDate, format);
			Calendar firstCa = Calendar.getInstance();
			Calendar secondCa = Calendar.getInstance();
			//判断前后日期哪个大
			if(first.before(second)) {
				firstCa.setTime(first);
				secondCa.setTime(second);
			}else {
				firstCa.setTime(second);
				secondCa.setTime(first);
			}
			//获取较小日期的年、月、日
		    int firstYear = firstCa.get(Calendar.YEAR);
		    int firstMonth = firstCa.get(Calendar.MONTH);
		    int firstDay = firstCa.get(Calendar.DATE);
		    //获取较大日期的年、月、日
		    int secondYear = secondCa.get(Calendar.YEAR);
		    int secondMonth = secondCa.get(Calendar.MONTH);
		    int secondDay = secondCa.get(Calendar.DATE);
		    //判断日期是否为同一年
		    if(firstYear == secondYear){
		    	result=secondMonth-firstMonth;//两个日期相差几个月，即月份差
		    }else{
		    	result=12 * (secondYear-firstYear) + secondMonth - firstMonth;//两个日期相差几个月，即月份差
		    }
		    //判断开始日期的天数是否大于结束日期天数，如果大于则不足一月，在结果中减去一个月
		    if(firstDay > secondDay && result > 0) {
		    	result = result - 1;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 日期检查,判断是否是合法日期.
	 * <P>
	 * @param value        要验证的值
	 * @param varValue   xml 规则上的值,日期格式 yyyy-MM-dd HH:mm:s
	 * @return boolean true:对的
	 */
	public static boolean isRightDate(String value,String varValue) {
		try{
			SimpleDateFormat format =	 new SimpleDateFormat(varValue);
			//若为true 2-31会自动转换为3-3
			format.setLenient(false);
			format.parse(value);
		}catch(ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 *  获取下一天
	 * @param strTime
	 * @param format
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getNextDay(String strTime, String format) { 
        Calendar cal = Calendar.getInstance(); 
        Date date = new Date(); 
        SimpleDateFormat sdf = new SimpleDateFormat(format); 
        String reTime = "";
        try { 
            date = sdf.parse(strTime); 
            cal.setTime(date); 
            cal.add(cal.DATE, 1); 
            reTime = sdf.format(cal.getTime());
            
        }catch (Exception e){ 
            e.printStackTrace(); 
        } 
        return reTime;
    } 
	/**
	 * 格式化时间.
	 * @author chylg
	 * @param date 日期
	 * @param format 输出日期格式
	 * @return 日期字符串
	 */
	public static String formatDate(Date date,String format){
		if (format==null || "".equals(format)) {
			format = DATE_FORMATE_STRING_A;
		}
		SimpleDateFormat sDateFormat = getDateFormat(format);
		//单实例,SimpleDateFormat非线程安全
		synchronized (sDateFormat) {
			return sDateFormat.format(date);
		}
	}
	
	/**
	 * 格式化时间,以默认yyyy-MM-dd HH:mm:ss格式输出.
	 * @author chylg
	 * @param date 日期
	 * @return 日期字符串.
	 */
	public static String formatDate(Date date){
		return formatDate(date,DATE_FORMATE_STRING_A);
	}
	
	/**
	 * 所属月第一天.
	 * @author chylg
	 * @param date 输入日期
	 * @param format  输出日期格式
	 * @return 日期字符串
	 */
	public static String firstDay(Date date,String format){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 当月第一天.
	 * @author chylg
	 * @param format 输出日期格式
	 * @return 日期字符串
	 */
	public static String firstDay(String format){
		return firstDay(new Date(),format);
	}
	
	/**
	 * 当月第一天，默认日期格式yyyy-MM-dd.
	 * @author chylg
	 * @return 日期字符串
	 */
	public static String firstDay(){
		return firstDay(DATE_FORMATE_STRING_B);
	}
	
	/**
	 * 所属月第一天，默认日期格式yyyy-MM-dd.
	 * @author chylg
	 * @param date  日期
	 * @return string
	 */
	public static String firstDay(Date date){
		return firstDay(date);
	}
	
	/**
	 * 所属月最后一天.
	 * @author chylg
	 * @param date 日期
	 * @param format 日期格式
	 * @return 日期字符串
	 */
	public static String lastDay(Date date,String format){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 当前月最后一天.
	 * @author chylg
	 * @param format 日期格式
	 * @return 日期字符串
	 */
	public static String lastDay(String format){
		return lastDay(new Date(),format);
	}
	
	/**
	 * 所属月最后一天，默认日期格式yyyy-MM-dd.
	 * @param date 某一日期
	 * @return string
	 * @author chylg
	 */
	public static String lastDay(Date date){
		return lastDay(date, DATE_FORMATE_STRING_B);
	}
	
	/**
	 * 当前月最后一天，默认日期格式yyyy-MM-dd.
	 * @author chylg
	 * @param format
	 * @return string
	 */
	public static String lastDay(){
		return lastDay(DATE_FORMATE_STRING_B);
	}
	
	/**
	 * 根据传入的日期计算近月（n月前/后），n为正数为n月后，n为负数为n月前.
	 * @param date  日期
	 * @param paramFormat 入参日期格式
	 * @param format  出参日期格式
	 * @param num  相邻月数
	 * @return
	 */
	public static String nearMonth(String date,String paramFormat,String format,int num) {
		Date calDate = getDateFromString(date, paramFormat);
		Calendar cal= Calendar.getInstance();
		cal.setTime(calDate);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+num);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 近月（n月前/后），n为正数为n月后，n为负数为n月前.
	 * @author chylg
	 * @param format 日期格式
	 * @param num 相邻月数
	 * @return 日期
	 */
	public static String nearMonth(String format,int num){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+num);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 近月（n月前/后），n为正数为n月后，n为负数为n月前。默认日期格式yyyy-MM.
	 * @author chylg
	 * @param num n月
	 * @return yyyy-MM
	 */
	public static String nearMonth(int num){
		return nearMonth(DATE_FORMATE_STRING_YM,num);
	}
	
	/**
	 * 下一个月，默认日期格式yyyy-MM.
	 * @author chylg
	 * @return string
	 */
	public static String nextMonth(){
		return nearMonth(DATE_FORMATE_STRING_YM,1);
	}
	
	/**
	 * 上一个月，默认日期格式yyyy-MM.
	 * @author chylg
	 * @return string
	 */
	public static String prevMonth(){
		return nearMonth(DATE_FORMATE_STRING_YM,-1);
	}
	
	/**
	 * 当前月，默认日期格式yyyy-MM.
	 * @author chylg
	 * @return string.
	 */
	public static String currMonth(){
		return nearMonth(DATE_FORMATE_STRING_YM,0);
	}
	
	/**
	 * 当前年份.
	 * @return int 当前年份
	 */
	public static int nowYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	/**
	 * 当前月份.
	 * @return int 当前月份
	 */
	public static int nowMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}
	/**
	 * 当日.
	 * @return int 当日
	 */
	public static int nowDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 根据传入日期计算近日（n日前/后），n为正数为n日后，n为负数为n日前。
	 * @param date  入参日期
	 * @param paramFormat 入参日期格式
	 * @param format  返回日期格式
	 * @param n   近日
	 * @return
	 */
	public static String nearDay(String date,String paramFormat,String format,int n) {
		Date caDate = getDateFromString(date, paramFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(caDate);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+n);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 近日（n日前/后），n为正数为n日后，n为负数为n日前。
	 * @author chylg
	 * @param format 日期格式
	 * @param num n月
	 * @return yyyy-MM
	 */
	public static String nearDay(String format,int n){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+n);
		return formatDate(cal.getTime(),format);
	}
	
	/**
	 * 近日（n日前/后），n为正数为n日后，n为负数为n日前。默认日期格式yyyy-MM-dd.
	 * @author chylg
	 * @param format 日期格式
	 * @param num n月
	 * @return yyyy-MM
	 */
	public static String nearDay(int n){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)+n);
		return formatDate(cal.getTime(),"yyyy-MM-dd");
	}
	
	public static void main(String[] args) throws ParseException {
		//System.out.println(nearDay("yyyyMMdd000000",-7));
		//System.out.println(nearDay("yyyyMMdd000000",1));
		//int result = getDateMinusMonth("19990405","20990406","yyyyMMdd");
		//System.out.println(result);
		System.out.println(nearMonth("20121004","yyyyMMdd","yyyy-MM-dd HH:mm:ss",-3));
		System.out.println(nearDay("20121003","yyyyMMdd","yyyy-MM-dd HH:mm:ss",30));
	}
}
