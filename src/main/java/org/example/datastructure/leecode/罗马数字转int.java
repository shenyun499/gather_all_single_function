package org.example.datastructure.leecode;

import util.HashMap;
import util.Map;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:20
 */
public class 罗马数字转int {
    public int romanToInt(String s) {
        Map<String,Integer> map = new HashMap<>();
        map.put("IV", 4);
        map.put("XL", 40);
        map.put("CD", 400);
        map.put("IX", 9);
        map.put("XC", 90);
        map.put("CM", 900);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        int total = 0;
        int i = 0;
        for (; i < s.length(); i++) {
            //容器中，s包含两个字符
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                total += map.get(s.substring(i, i + 2));
                i++;
            } else {
                total += map.get(s.substring(i, i + 1));
            }
        }
        return total;
    }
}
