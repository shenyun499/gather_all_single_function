package org.example.datastructure.leecode.dtgh;

/**
 * @Description
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * 动态规划
 * @Author xuexue
 * @Date 2020/1/6 15:25
 */
public class 最长回文子串 {
    public String longestPalindrome(String s) {
        //获取字符串长度
        int length = s.length();
        //[][]范围是回文子串则存储true，否则false
        boolean[][] bol = new boolean[length][length];

        //最长回文子串长度
        int maxLength = 0;

        //最长回文子串
        String str = "";

        //所有长度
        for (int len = 1; len <= length; len++) {
            for (int start = 0; start < length; start++) {
                //长度之差索引 差值根据len决定，每次根据第一次for增加
                int end = start + len - 1;

                //索引超出，跳出循环
                if (end >= length) {
                    break;
                }
                //判断在区间start和end中，是否是回文子串（根据去掉头尾判断是否回文，然后再判断头尾是否相等），是存储true否则false
                bol[start][end] = (len == 1 || len == 2 || bol[start + 1][end - 1])
                        && s.charAt(start) == s.charAt(end);

                //如果子串是回文子串，并且大于原本存储的子串长度，则进行替换子串操作
                if (bol[start][end] && s.substring(start, end + 1).length() > maxLength) {
                    str = s.substring(start, end + 1);
                    maxLength = str.length();
                }
            }
        }
        return str;
    }


}
