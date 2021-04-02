package date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jdk8时间新特性-LocalDateTime 1.8之前对操作时间api一直是个难题
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-03-26
 */
public class JDK8DateFormat {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        // 获取前一天的时间
        System.out.println(now.minusDays(1));
        // 获取前一天 前一个小时的时间
        System.out.println(now.minusDays(1).minusHours(1));

        // jdk1.8线程安全
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf2.format(now));

        System.out.println((true & true));

    }
}
