package tools;

/**
 * 判断类
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2020-07-21
 */
public class JudgeUtils {
    public static <T> boolean isNotEmpty(T ts) {
        return !isEmpty(ts);
    }

    public static <T> boolean isEmpty(T ts) {
        if (null == ts) {
            return true;
        } else {
            return false;
        }
    }
}
