package com.arithmetic.sort;

import com.arithmetic.hash.HashTable;
import com.arithmetic.hash.StudentInfo;

import java.util.Arrays;

/**
 * @Description
 * 测试
 * @Author xuexue
 * @Date 2019/5/3016:53
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 4, 1, 8, 5, 9, 0};
        Test test = new Test();
        test.insertSort(arr);
        System.out.println(Arrays.toString(arr));

        System.out.println(System.getProperty("os.arch"));

    }

    public void test() {
        StudentInfo studentInfo1 = new StudentInfo("zhangsan",10);
        StudentInfo studentInfo2 = new StudentInfo("lisi",20);
        StudentInfo studentInfo3 = new StudentInfo("wangwu",5);
        StudentInfo studentInfo4 = new StudentInfo("zhaoliu",40);

        HashTable hashTable = new HashTable();
        hashTable.put(studentInfo1);
        hashTable.put(studentInfo2);
        hashTable.put(studentInfo3);
        hashTable.put(studentInfo4);

        System.out.println(hashTable);
    }

    // 冒泡排序-有序时间复杂度O(n^2)，稳定
    public void dubble(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {//比较的趟数
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 如果前面的数小于后面的数，就交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 冒泡排序-优化版本
    public void dubble2(int[] arr) {
        boolean isSort = false;
        for (int i = 0; i < arr.length - 1; i++) {//比较的趟数
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 如果前面的数小于后面的数，就交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSort = false;
                }
            }
            if (isSort) {
                return;
            }
        }
    }

    // 选择排序-不稳定33145
    public void chooseSort(int[] arr) {
        // 选择一个数，然后比较大小，最终确定一个最大值，然后交换
        // 保存最小索引数
        int minIndex = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            // 选择i作为最小值
            minIndex = i;
            for (int j = i; j < arr.length; j++) {
                minIndex = arr[minIndex] < arr[j] ? minIndex : j;
            }
            //交换
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
    }

    // 插入排序-稳定
    public void insertSort(int[] arr) {
        // 每次选择一个，往前插入到比自己小的位置，如果比自己大则结束
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            int num = arr[i];
            for (j = i - 1; j >= 0; j--) {
                if (num > arr[j]) {
                    break;
                }
                arr[j + 1] = arr[j];
            }
            // 插入
            if (num != arr[i]) {
                arr[j + 1] = num;
            }
        }
    }
}
