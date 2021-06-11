package org.example.datastructure.leecode.string;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/1/7 21:27
 */
public class 字符串转换为整数 {

    public static void main(String[] args) {
        String s = "-2147483648";
        System.out.println(myAtoi(s));
    }

    public static int myAtoi(String str) {
        if (str.isEmpty() || str.length() == 0) {
            return 0;
        }
        int i = 0;
        char[] chars = str.toCharArray();
        while (i < str.length() && chars[i] == ' ') {
            i++;
        }

        //此时chars[i]不为空
        if (i == str.length()) {
            return 0;
        }

        //为+号
        int flag = 1;
        if (chars[i] == '+') {
            flag = 1;
            i++;
        } else if (chars[i] == '-') {
            flag = -1;
            i++;
        } else if (chars[i] < '0' || chars[i] > '9') {
                return 0;
        }

        //取数字
        int res = 0;
        int temp = 0;
        for (; i < str.length(); i++) {
            if (chars[i] < '0' || chars[i] > '9') {
                break;
            }
            temp = chars[i] - '0';
            if (flag == 1 && (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && temp > 7))) {
                return Integer.MAX_VALUE;
            }
            if (flag == -1 && (-res < Integer.MIN_VALUE / 10 || (-res == Integer.MIN_VALUE / 10 && -temp < -8))) {
                return Integer.MIN_VALUE;
            }

            res = res * 10 + temp;
        }

        return res * flag;
    }
}
