package org.example.datastructure.leecode.array;

import util.ArrayList;

/**
 * @Description
 * @Author xuexue
 * @Date 2020/2/820:12
 */
public class 输出两数之和为S乘积是最小的数组 {
    //输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
    public static ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        int l = 0, r = array.length - 1;
        int temp = 0;
        ArrayList<Integer> list = new ArrayList<>();
        if (array.length == 0) {
            throw new RuntimeException("没有找到两个数");
        }
        while (l < r) {
            //如果比sum小则右移，否则左移
            if (sum > array[l] + array[r]) {
                l++;
            } else if (sum < array[l] + array[r]) {
                r--;
            } else {
                temp = array[l] * array[r];
                //sum == array[l] + array[r]
                if (list.size() != 0 && temp > array[list.get(0)] * array[list.get(1)]) {
                    list.set(0, l);
                    list.set(1, r);
                }
                if (list.size() == 0) {
                    list.add(l);
                    list.add(r);
                }
                r--;
            }
        }

        return list;
    }
}
