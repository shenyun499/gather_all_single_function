package regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式Pattern - Matcher的使用
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2022-09-07
 */
public class RegularTest {
    public static void main(String[] args) {
        String example = "12_12_";
        // 加了括号，下面matcher.group可以获得以括号分组
        Pattern pattern = Pattern.compile("(\\d+)_(\\d+)_");
        Matcher matcher = pattern.matcher(example);
        if (matcher.find()) {
            int i = matcher.groupCount();
            for (int j = 0; j <= i; j++) {
                System.out.println(matcher.group(j));
            }
        }
    }
}

