package org.example.datastructure.leecode;

import util.HashMap;
import util.Map;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:20
 */
public class int转罗马数字 {
    public String intToRoman(int num) {
        Map<Integer, String> map = new HashMap<>();
        map.put(4, "IV");
        map.put(40, "XL");
        map.put(400, "CD");
        map.put(9, "IX");
        map.put(90, "XC");
        map.put(900, "CM");
        map.put(1, "I");
        map.put(5, "V");
        map.put(10, "X");
        map.put(50, "L");
        map.put(100, "C");
        map.put(500, "D");
        map.put(1000, "M");
        int[] arr = new int[] {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        return digui(map, num, arr, arr.length - 1);
    }

    public String digui(Map<Integer, String> map, int num,int[] arr, int n) {
        if (num == 1) {
            return "I";
        }
        if (map.containsKey(num)) {
            return map.get(num);
        }
        while (num < arr[n]) {
            n--;
        }
        return map.get(arr[n]) + digui(map, num - arr[n], arr, n);
    }
}
