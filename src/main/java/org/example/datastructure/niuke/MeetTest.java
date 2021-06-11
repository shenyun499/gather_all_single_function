package org.example.datastructure.niuke;

import org.junit.Test;

import util.*;

/**
 * @Description
 * 面试手撕准备用
 * @Author xuexue
 * @Date 2020/3/12 10:02
 */
public class MeetTest {
    public static void main(String[] args) {
        String text = "199";
        try {
            text = text.concat(".5");
            double de = Double.parseDouble(text);
            text = Double.toString(de);
            int status = (int)Math.ceil(Double.valueOf(text).doubleValue());
            System.out.print(status);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }

    }

    @Test
    public void test() {
        System.out.println(check(6, 6));
        HashMap<String, String> map = new HashMap<String, String>() {{put("a", "a");}};
        printHM(map);
    }

    public static boolean check(int a, int b) {
        return (a > 5 && b <= 5) || (b > 5 && a <= 5) ? true : false;
    }
    public void printHM(HashMap<String, String> map) {
        for (String key : map.keySet()) {
            System.out.println(key + "-fanggeek-" + map.get(key));
        }
    }

    @Test
    public void s() {
        List<String> list = Arrays.asList("abcd", "Ab1", "ABC2", "abE", "abCD");
        list.stream()
                .map(String :: toUpperCase)
                .filter("ABCDEFGH" :: startsWith)//过滤出以ABCDEFGH开头的字符
                .map(String::toLowerCase)//将map集合转为小写字母
                .distinct()//去重
                .forEach(System.out :: println);//打印
    }
}
