package org.example.datastructure.com.arithmetic.sort;

import lang.reflect.Method;
import util.Arrays;

/**
 * @Description
 * 功能：实现冒泡排序算法
 * 时间复杂度O(n^2)；空间复杂度O(1)；稳定；算法复杂度最好O(n),需要加条件实现，最坏情况是O(n^2)
 * @Author xuexue
 * @Date 2019/5/3117:03
 */
public class DubbleSort {
    public static void main(String[] args) {
        //初始化一个int类型的数组
        int[] arr = {5,3,4,2};

        try {
            Class dubbleSort = Class.forName("com.arithmetic.sort.Test");
            Test instance = (Test)dubbleSort.newInstance();
            Method bubbleSort = dubbleSort.getMethod("test");
            bubbleSort.invoke(instance);
        } catch (Exception e ) {
            e.printStackTrace();
        }

        //输出未排序前的数组
        System.out.println(Arrays.toString(arr));

        //调用冒泡排序算法，对arr进行排序
        bubbleSort(arr);

        //输出排序后的数组
        System.out.println(Arrays.toString(arr));

    }




    /**
     * 功能：
     * 对传入的数组进行冒泡排序
     * 从小到大排序
     * 啥为冒泡？就是每次确定一个最大的数
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int temp;
        //从小到大
        for (int i = 0; i < arr.length - 1; i++) {//排序的趟数
            for (int j = 0; j < arr.length - i -1; j++) {//每次确定一个最大的到后面
                if (arr[j] > arr[j+1]) {
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
/*
        //从大到小
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i -1; j++) {
                if (arr[j] < arr[j+1]) {
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
*/
    }



}
