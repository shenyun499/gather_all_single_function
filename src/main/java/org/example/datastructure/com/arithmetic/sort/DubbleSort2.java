package org.example.datastructure.com.arithmetic.sort;

import lang.reflect.Method;
import util.Arrays;

/**
 * @Description
 * 功能：冒泡排序算法的改进 最好的情况时间复杂度为O(n)，当原来就是正序时
 * @Author xuexue
 * @Date 2019/5/31 17:03
 */
public class DubbleSort2 {
    public static void main(String[] args) {
        //用于判断第一趟排序时，是否进行了交换，如果没有则是有序，不需要继续排序
        boolean isSort = true;

        int[] items = new int[]{3, 5, 2, 1, 4, 10};

        //冒泡排序实现
        for (int i = 0; i < items.length -1; i++) {//共要比较的趟数 n-1躺
            //每一躺的比较次数 n-1/n-2/n-3/../1 算法复杂度 (1+n-1)n/2=n^2/2=n^2
            for (int j = 0; j < items.length - i - 1; j++) {
                //比较 前一个元素比后一个元素大则交换
                if (items[j] > items[j + 1]) {
                    int temp = items[j + 1];
                    items[j + 1] = items[j];
                    items[j] = temp;
                    isSort = false;
                }
            }
            //判断是否需要继续排序（仅仅对第一次正序的不需要排序）
            if (isSort)
                return;
        }
    }

}
