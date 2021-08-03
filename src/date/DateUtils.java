package date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-11-04
 */
public class DateUtils {

    /**
     * 根据传入的时间格式，得到LocalDateTime类型的时间
     *
     * @param dateTime 字符类型时间
     * @param formatType 时间格式
     * @return LocalDateTime类型的时间
     */
    public static LocalDateTime getFormatDate(String dateTime, String formatType) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(formatType);
        LocalDateTime time = LocalDateTime.now();
        //String localTime = df.format(time);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, df);
        //System.out.println("LocalDateTime转成String类型的时间：" + localTime);
        System.out.println("\nString类型的时间转成LocalDateTime：" + localDateTime);
        return localDateTime;
    }

    /**
     * 获取上一个月的时间
     *
     * @param dateStr 时间字符串
     * @param formatType 格式化
     * @return
     */
    public static LocalDateTime getPreviousMonthDate(String dateStr, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        // 转成date类型操作
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 减一个月操作
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);

        // 转回LocalDateTime
        DateTimeFormatter df = DateTimeFormatter.ofPattern(formatType);
        return LocalDateTime.parse(sdf.format(calendar.getTime()), df);
    }

    /**
     * 一年中的第几周，jdk7 api
     */
    @Test
    public void getWeekOfYear() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.WEEK_OF_YEAR);
        System.out.println(i);
    }

    /**
     * 比较日期
     *
     * @param nextUpdateDate
     * @return
     */
    public static boolean isNow(LocalDateTime nextUpdateDate) {
        LocalDateTime nowTime = LocalDateTime.now();
        Duration duration = Duration.between(nowTime, nextUpdateDate);
        return duration.toDays() == 0 ? true : false;
    }

    public void dateT() {
        // TODO: java8新特性LocalDateTime字符串转时间比较大小
        // String time1 = "2019-06-26 19:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//有漏洞，建议不要用
//        Date date1;
//        try {
//            date1 = sdf.parse(time1);
//            Date date2 = new Date();
//            System.out.println(date1.before(date2));
//            System.out.println(date1.after(date2));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //第一种
        String time1 = "2019-06-26 19:00:00";
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time1, dtf2);
        System.out.println(localDateTime.isBefore(LocalDateTime.now()));//你的时间在当前时间之前是true
        System.out.println(localDateTime.isAfter(LocalDateTime.now()));//在当前时间之后是false
        // 第二种
        // LocalDateTime now = LocalDateTime.now();
        // Duration duration = Duration.between(now,now);
       /* System.out.println(duration);
        long days = duration.toDays(); //相差的天数
        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        System.out.println(millis);
        //第三种
        long mills = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(mills >= System.currentTimeMillis());*/
    }

    //LocalDate -> Date
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    //LocalDateTime -> Date
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    //Date -> LocalDate
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //Date -> LocalDateTime
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
