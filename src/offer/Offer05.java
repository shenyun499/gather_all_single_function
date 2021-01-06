package offer;

/**
 * 剑指offer 05题
 *
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *
 * 示例 1：
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 * 限制：
 * 0 <= s 的长度 <= 10000
 *
 * 思路：遍历字符串，遇到空格则将字符累加%20，否则将字符串原型加回去
 * 时间复杂度为O(n),空间复杂度为O(n)
 *
 *
 * @author ：HUANG ZHI XUE
 * @date ：Create in 2021-01-06
 */
public class Offer05 {
    public static void main(String[] args) {
        if (" ".equals(' ')) {
        }
    }

    public static String replaceSpace(String s) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            // 这里用了" ".equals(s.charAt(i))导致判断不出来，字符不可以用equals
            if (' ' == s.charAt(i)) {
                sf.append("%20");
            } else {
                sf.append(s.charAt(i));
            }
        }
        return sf.toString();
    }
}
