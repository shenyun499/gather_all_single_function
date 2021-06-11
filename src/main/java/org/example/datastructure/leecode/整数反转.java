package org.example.datastructure.leecode;

/**
 * @Description
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * @Author xuexue
 * @Date 2020/1/6 21:32
 */
public class 整数反转 {

    public int reverse(int x) {
        String str = x + "";
        if (x < 0) {
            str = str.substring(1);
        }
        char[] chars = str.toCharArray();
        char temp;
        int i = 0, j = chars.length - 1;
        //双指针交换
        while (i < chars.length / 2) {
            temp = chars[i];
            chars[i] = chars[j];
            chars[j--] = temp;
            i++;
        }
        String s = new String(chars);
        int newX;
        try {
            newX = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
        if ((x > 0 && newX < 0) || (x < 0 && -newX > 0)) {
            return 0;
        }
        if (x < 0) {
            return -newX;
        }
        return newX;
    }

    public int reverse2(int x) {
        int value = 0;
        int pop = 0;
        while (x != 0) {
            //判断是否超过最大范围
            if ((value > Integer.MAX_VALUE / 10) || (value == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if ((value < Integer.MIN_VALUE / 10) || (value == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            pop = x % 10;
            x /= 10;
            value = value * 10 + pop;
        }
        return value;
    }

}
