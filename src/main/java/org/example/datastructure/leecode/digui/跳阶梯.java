package org.example.datastructure.leecode.digui;

import util.HashMap;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/819:56
 */
public class 跳阶梯 {
    //递归
    public static int next(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return next(n - 1) + next(n - 2);
    }

    //递归 -- 优化，备忘录
    public static int next2(int n, HashMap<Integer, Integer> hashMap) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (hashMap.containsKey(n)) {
            return hashMap.get(n);
        }
        int value = next2(n - 1, hashMap) + next2(n - 2, hashMap);
        hashMap.put(n, value);
        return value;
    }
}
