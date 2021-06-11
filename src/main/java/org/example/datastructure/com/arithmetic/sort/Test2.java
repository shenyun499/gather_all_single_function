package org.example.datastructure.com.arithmetic.sort;

import util.Arrays;

/**
 * @Description
 * @Author xuexue
 * @Date 2019/12/109:51
 */
public class Test2 {

    public static void main(String[] args) {
        int[] arrs = new int[]{3, 5, 2, 1, 4, 10};
        //insertSort(arrs);//插入排序算法进行排序
        chooseSort(arrs);//选择排序算法进行排序
        System.out.println(Arrays.toString(arrs));
    }

    /**
     * 插入排序
     * 1、确定插入排序的数，从第二个开始选择
     * 2、选择插入排序的数，保存为num
     * 3、计算num前一个数的索引
     * 4、进行检查，如果num小于前面的数，则将前一个数往后移，若比前一个数大，则结束此次循环
     * 5、结束时的位置保存num
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            //选择插入排序的数，保存为num
            int num = arr[i];

            //计算num前一个数的索引
            int preIndex = i - 1;

            for (; preIndex >= 0; preIndex--) {
                //进行检查，如果num小于前面的数，则将前一个数往后移，若比前一个数大，则结束此次循环
                if (num < arr[preIndex])
                    arr[preIndex + 1] = arr[preIndex];
                else
                    break;
            }
            //结束时的位置保存num
            if (num != arr[i])
                arr[preIndex + 1] = num;
        }
    }

    /**
     * 选择排序
     * 1、确定需要选择的数个数，确定循环（n-1)
     * 2、确定选择作为排序的数的索引index
     * 3、将此数与数组进行比较，如果大于某数，则此索引更新为某数，继续比较，继续更新，直到遍历完，
     * 记录最小数的索引成功
     * 4、将最小数的索引index与选择的数的索引值进行交换
     *
     * @param arr
     */
    public static void chooseSort(int[] arr) {
        //1、确定需要选择的数个数，确定循环（n-1)
        for (int i = 0; i < arr.length -1; i++) {
            //2、确定选择作为排序的数的索引index
            int index = i;

            //3、将此数与数组进行比较，如果大于某数，则此索引更新为某数，继续比较，继续更新，直到遍历完，
            //记录最小数的索引成功
            for (int j = i + 1; j < arr.length; j++) {
                //index = arr[index] > arr[j] ? j : index;//用三目可以考虑
                if (arr[index] > arr[j])
                    index = j;
            }

            //4、将最小数的索引index与选择的数的索引值进行交换
            if (index != i) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }

    }
}
