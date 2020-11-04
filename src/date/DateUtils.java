package date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
