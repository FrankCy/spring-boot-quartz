package com.sb.quartz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @description：
 * @author: Yang.Chang
 * @project: spring-boot-quartz
 * @package: com.sb.quartz.util、
 * @email: cy880708@163.com
 * @date: 2019/3/20 下午3:41
 * @mofified By:
 */
public class DateUtil {


    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String STR_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String STR_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String STR_ERROR = "error";

    /**
     *
     * 将日期字符串转换成长时间格式yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static Date longStrToDate(String strDate) throws ParseException {
        if(StringUtils.isEmpty(strDate)) {
            return null;
        } else {
            return new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS).parse(strDate);
        }

    }
    /**
     * 将长时间格式yyyy-MM-dd HH:mm:ss字符串转换为日期
     * @throws ParseException
     */
    public static String dateToString(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);
        String dateString = formatter.format(date);
        return dateString;

    }
    /**
     * 获取系统当前时间戳
     * @return
     */
    public static String getCurUnixTimeStamp(){
        long unixTimeStamp = System.currentTimeMillis() /1000L;
        return String.valueOf(unixTimeStamp);
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static Integer getCurUnixTimeStampInt(){
        return Integer.valueOf(getCurUnixTimeStamp());
    }

    /**
     * 把unix时间戳转换为时间字符串 [格式：“yyyy-MM-dd HH:mm:ss”]
     * @param timeStamp 时间戳
     * @return 格式化好的时间字符串
     */
    public static String toDate(long timeStamp) {
        return toDate(timeStamp, STR_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 把unix时间戳转换为时间字符串 [格式：“yyyy-MM-dd”]
     *
     * @param timeStamp
     *            时间戳
     * @return 格式化好的时间字符串
     */
    public static String toDateShort(long timeStamp) {
        return toDate(timeStamp, STR_YYYY_MM_DD);
    }

    /**
     * 把unix时间戳转换为时间字符串
     * @param timeStamp 时间戳
     * @param dateFormat 时间格式 yyyy-MM-dd HH:mm:ss a
     * @return 格式化好的时间字符串
     */
    public static String toDate(long timeStamp, String dateFormat) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date(timeStamp));
    }

    public static Integer getUnixTimeStampByDate(Date date) {
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 获取当前时间的 年月日时分秒毫秒
     */
    public static String getCurrentTime(){
        Calendar caldendar = Calendar.getInstance();

        String year = String.valueOf(caldendar.get(Calendar.YEAR));
        String month = String.valueOf(caldendar.get(Calendar.MONTH)+1);
        String day = String.valueOf(caldendar.get(Calendar.DATE));
        String hour = String.valueOf(caldendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(caldendar.get(Calendar.MINUTE));
        String sencond = String.valueOf(caldendar.get(Calendar.SECOND));
        String millisecond = String.valueOf(caldendar.get(Calendar.MILLISECOND));

        String fileName = year + prefixString(month) + prefixString(day) + prefixString(hour) + prefixString(minute) + prefixString(sencond) + prefixString2(millisecond);

        StringBuilder fileNameBuilder = new StringBuilder(fileName);
        while (fileNameBuilder.length() < 17){
            fileNameBuilder.append("0");
        }
        return fileNameBuilder.toString();
    }

    private static String prefixString(String s) {
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    private static String prefixString2(String s) {
        if (s.length() == 1) {
            s = "00" + s;
        } else if (s.length() == 2) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 转换成format
     */
    public static String dateToStr(Date d, String format) {
        if(d == null) {
            return "";
        } else {
            return new SimpleDateFormat(format).format(d);
        }
    }
    /**
     * 将指定的日期加上指定的天数
     */
    public static Date addDays(Date d, int days) {
        if(days == 0) {
            return d;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.DATE, days);
            setNoTime(c);
            return c.getTime();
        }
    }

    /**
     * 将指定的日期加上指定的天数
     */
    public static Date addMonth(Date d, int Months) {
        if(Months == 0) {
            return d;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.MONTH, Months);
            setNoTime(c);
            return c.getTime();
        }
    }

    /**
     * 将指定的日期加上指定的小时
     */
    public static Date addHours(Date d, int hour) {
        if(hour == 0) {
            return d;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, hour);// 24小时制
            d = cal.getTime();
            return  d;
        }
    }
    /**
     * 将指定的日期加上指定的秒
     */
    public static Date addSecond(Date d, int second) {
        if(second == 0) {
            return d;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.SECOND, second);// 24小时制
            d = cal.getTime();
            return  d;
        }
    }
    /**
     * 把指定日期的时分秒设为0
     * @param c 指定的日期
     */
    private static void setNoTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
    }
    /**
     * 获取随机的数值。
     * @param length 长度
     * @return
     */
    public static String getRandom(Integer length){
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        int n = 20;
        if(null != length && length > 0){
            n = length;
        }
        boolean[]  bool = new boolean[n];
        int randInt = 0;
        for(int i = 0; i < length ; i++) {
            do {
                randInt  = rand.nextInt(n);

            }while(bool[randInt]);

            bool[randInt] = true;
            result.append(String.valueOf(randInt));
        }
        return result.toString();
    }


    /**
     * 把时间字符串转换为时间戳
     * @param time 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 10位Integer类型时间戳
     */
    public static Integer date2TimeStamp(String time){
        Integer timeStemp = 0;
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);
        try {
            Date date = simpleDateFormat .parse(time);
            timeStemp = Integer.parseInt(String.valueOf(date.getTime()/1000));
        }catch (Exception e){
            logger.error(STR_ERROR, e);
        }
        return timeStemp;
    }


    /**
     * 把时间字符串转换为时间戳
     * @param time 时间格式 yyyy-MM-dd HH:mm:ss
     * @return 10位Integer类型时间戳
     */
    public static Date date2TimeStamp2(String time){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(STR_YYYY_MM_DD);
        Date date = null;
        try {
            date = simpleDateFormat .parse(time);
        }catch (Exception e){
            logger.error(STR_ERROR, e);
        }
        return date;
    }
    private static final List<String> list= new ArrayList<>();
    //测试人员帐号
    static {
        list.add("10100167");
        list.add("10100157");
        list.add("10100559");
        list.add("10100161");
        list.add("10100159");
        list.add("10100464");
        list.add("10100209");
        list.add("10100162");
    }
    /**
     * 判断当前时间是否为指定直播时间
     * @return
     */
    public static boolean allowLiveBroadcast (String startTime1,String endTime1) {
        try {
            logger.debug("startTime1:{},endTime1:{}",startTime1,endTime1);
            startTime1 = startTime1+ ":00";
            endTime1 = endTime1+ ":59";
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
            Date now =null;
            Date beginTime = null;
            Date endTime = null;

            now = df.parse(df.format(new Date()));
            beginTime = df.parse(startTime1);
            endTime = df.parse(endTime1);

            Calendar date = Calendar.getInstance();
            date.setTime(now);

            Calendar begin = Calendar.getInstance();
            begin.setTime(beginTime);

            Calendar end = Calendar.getInstance();
            end.setTime(endTime);

            if ((date.after(begin) && date.before(end)) || (date.equals(begin) || date.equals(end))) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            logger.error(STR_ERROR, e);
        }

        return false;

    }

    /**
     * 创建Timestamp
     * @return
     */
    public static Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间 格式：年月日
     * @return
     */
    public static String getActivityTime() {
        Format format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }


    /**
     * 凌晨
     * @param date
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        if (date != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            //时分秒（毫秒数）
            long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
            //凌晨00:00:00
            cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

            if (flag == 0) {
                return cal.getTime();
            } else if (flag == 1) {
                //凌晨23:59:59
                cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
            }
            return cal.getTime();
        }else{
            return null;
        }

    }

    /**
     * 返回以当前时期准的开始时间
     * @param date
     * @return
     */
    public static Date getStartDateTime(Date date) {
        return weeHours(date, 0);
    }

    /**
     * 返回以当前时期准的结束时间
     * @param date
     * @return
     */
    public static Date getEndDateTime(Date date){
        return weeHours(date, 1);
    }

    /**
     * 获取剩余秒数
     * @param endTime
     * @return
     */
    public static Long getLastTimeSecond(String endTime){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            Date now = df.parse(df.format(new Date()));
            Date d2 = df.parse(endTime);
            long diff = (d2.getTime() - now.getTime())/1000;
            return diff;
        }catch (Exception e){
        }
        return 0L;
    }

    /**
     * 比较两个时间大小
     * @param nowTime
     * @param updateTime
     * @return
     */
    public static int compaerTime(Date nowTime, Date updateTime) {

        //容错
        if(nowTime == null || updateTime == null) {
            return 0;
        }

        //做差，取出相差毫秒数
        long i = nowTime.getTime() - updateTime.getTime();

        //定义一个月为30天，取30天的毫秒数
        //一天86400000毫秒
        long oneMonthTime = (new BigDecimal(86400000).multiply(new BigDecimal(30))).longValue();
        if(i > oneMonthTime) {
            //证明超过一个月
            return 1;
        }
        return 0;

    }


    public static Date strToDate(String strDate) {
        Date parse = null;
        try {
            parse = new SimpleDateFormat(STR_YYYY_MM_DD).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public static void main(String[] args) {

        System.out.println(getCurUnixTimeStamp());
    }
    /**
     * 给时间加上几个小时
     * @param date 当前时间 格式：yyyy-MM-dd HH:mm:ss
     * @param hour 需要加的时间
     * @return
     */
    public static Date addDateMinut(Date date, int hour){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return new Timestamp(System.currentTimeMillis());
        } else {
            //显示输入的日期
            System.out.println("front:" + format.format(date));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // 24小时制
            cal.add(Calendar.HOUR, hour);
            date = cal.getTime();
            //显示更新后的日期
            System.out.println("after:" + format.format(date));
            cal = null;
            return date;
        }

    }

}
